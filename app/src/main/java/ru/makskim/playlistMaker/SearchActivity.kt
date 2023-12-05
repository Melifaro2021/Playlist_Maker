package ru.makskim.playlistMaker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var inputEditText: EditText // 1. Строка ввода текста в поле поиска
    private var searchQuery: String = DEF_SEARCH // 2. Поисковый запрос
    private lateinit var searchField: ConstraintLayout // 3. Поле поиска
    private lateinit var rvSearchSongs: RecyclerView // 4. Определим RecyclerView

    private val trackList = ArrayList<Track>()
    private val adapter = SearchAdapter()

    private val nothingFound by lazy { findViewById<LinearLayout>(R.id.nothing_found_layout) } // Заглушка ничего не найдено
    private val noInternet by lazy { findViewById<LinearLayout>(R.id.no_internet_layout) } // Заглушка нет интернета
    private val searchRefreshButton by lazy { findViewById<Button>(R.id.search_refresh) } // Кнопка обновить

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputEditText = findViewById(R.id.inputEditText) // 1. Строка ввода текста в поле поиска
        searchField = findViewById(R.id.searchField) // 3. Поле поиска
        rvSearchSongs = findViewById(R.id.rv_search_songs) // 4. Определим RecyclerView
        val clearButton = findViewById<ImageView>(R.id.clearSearchFieldIcon) // Иконка крестика

        adapter.tracks = trackList
        rvSearchSongs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSearchSongs.adapter = adapter

        if (savedInstanceState != null){
            inputEditText.setText(savedInstanceState.getString(KEY, DEF_SEARCH) )
        }

        initBackButton() // Стрелка назад, вызов

        clearButton.visibility = View.INVISIBLE
        clearButton.setOnClickListener{
            inputEditText.text.clear() // Очистить поле поиска
            hideKeyboard() // Скрываем клавиатуру после очистки поля
            trackList.clear() // Очистить результаты поиска
            adapter.notifyDataSetChanged()// полностью перерисовать весь список
        }
        // логика по работе с введённым значением
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) =  Unit
            /**
             * onTextChanged: внесены изменения, неĸоторые символы тольĸо что были заменены.
             * Теĸст не может быть изменён. Это событие используется, ĸогда нам нужно увидеть,
             * ĸаĸие символы в теĸсте являются новыми.
             */
            /**
             * Этот метод вызывается, чтобы уведомить вас о том, что в пределах <code>s</code>:
             * символы <code>count</code>, начинающиеся с <code>start</code>
             * только что заменили старый текст длиной <code>до</code>.
             * Попытка внести изменения в <code>s</code> из этот обратный вызов.
             */
            override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int)  {
                clearButton.visibility = clearButtonVisibility(s)
                searchQuery = inputEditText.text.toString()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        /**
         * на клавиатуре в правой нижней её части кнопка переноса  строки будет заменена на кнопку Done
         * Чтобы обработать нажатие на кнопку Done, к соответствующему экземпляру EditText
         * нужно добавить специального слушателя:
        */
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendSearch()// ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ startSearch
                hideKeyboard() // Cкрыть клавиатуру после поиска
            }
            false
        }
        // Выполнить поиск при нажатии на кнопку обновить
        searchRefreshButton.setOnClickListener {
            sendSearch()
        }
    }

    // Функция: Стрелка назад, вернуться на предыдущую страницу
    private fun initBackButton() {
        val backButton = findViewById<ImageView>(R.id.arr_back_search_to_main)
        backButton.setOnClickListener {
            finish()
        }
    }
    // Функция для скрытия клавиатуры
    private fun hideKeyboard() {
        val inputMethodManager =  getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }
    // Функция скрыть или показать крестик в поле поиска
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    /** Хранение данных START
     * Переопределяем метод onSaveInstanceState (Сохранение состояния экземпляра),
     * чтобы сохранить текст из EditText (search_query) в Bundle методом putString
    */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, searchQuery)
    }
    /**
     *  Переопределить метод onRestoreInstanceState(Восстановление состояния экземпляра),
     * чтобы достать данные из Bundle при помощи метода getString и установить их в EditText.
    */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        inputEditText.setText(savedInstanceState.getString(searchQuery))
    }
    // Хранение данных END
    // Начать поиск песен
    private fun sendSearch() {
        rvSearchSongs.visibility = View.VISIBLE
        nothingFound.visibility = View.GONE
        noInternet.visibility = View.GONE
        val searchRequest = inputEditText.text

        if (searchRequest != null) {
            iTunesService.search(searchRequest.toString())
                .enqueue(object : Callback<SearchResult> {
                @SuppressLint("NotifyDataSetChanged")
                // вызывается, когда сервер дал нам ответ для нашего запроса
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    Log.d("TRANSLATION_LOG", "Status code: ${response.code()}")
                    trackList.clear()
                    if (response.isSuccessful) { // Страница открывается
                        val songs = response.body()?.results
                        if (songs != null) {
                            trackList.addAll(songs)// Если есть результаты поиска
                        }  else {
                            nothingFound.visibility = View.VISIBLE //показать заглушку ничего не найдено
                        }
                    }  else {
                        noInternet.visibility = View.VISIBLE //показать заглушку пропало соединение
                    }
                    adapter.notifyDataSetChanged()// полностью перерисовать весь список
                }
                // вызывается, когда мы не смогли установить соединение с сервером
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    trackList.clear()
                    noInternet.visibility = View.VISIBLE
                }
            })
        }
    }
/**
 * В Kotlin для создания константной переменной мы используем companion object.
 * Ключ должен быть константным, чтобы мы точно знали, что он не изменится
 */
    companion object {
        const val KEY = "search"
        const val DEF_SEARCH = ""
    }
}