package ru.makskim.playlist_maker

import android.annotation.SuppressLint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.text.TextWatcher
import android.text.Editable
import androidx.recyclerview.widget.RecyclerView
import ru.makskim.playlist_maker.MockPlaylist.mockPlaylist

class SearchActivity : AppCompatActivity() {
    private lateinit var inputEditText: EditText // 1. Поле поиска
    private lateinit var searchQuery: String // 2. Поле ввода текста
    private lateinit var searchField: ConstraintLayout // 3. Иконка крестика
    private lateinit var rvSearchSongs: RecyclerView // 4.
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        rvSearchSongs = findViewById(R.id.rv_search_songs)

        searchField = findViewById(R.id.searchField) // 1. Поле поиска
        inputEditText = findViewById(R.id.inputEditText) // 2. Поле ввода текста
        val clearButton = findViewById<ImageView>(R.id.clearSearchFieldIcon) // 3. Иконка крестика

        val searchAdapter = SearchAdapter(mockPlaylist)
        rvSearchSongs.adapter = searchAdapter

        initBackButton() // Стрелка назад, вызов

        clearButton.visibility = View.INVISIBLE
        clearButton.setOnClickListener{
            inputEditText.text.clear() // Очистить поле поиска
            hideKeyboard() // Скрываем клавиатуру после очистки поля
        }
        // логика по работе с введённым значением
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }
            override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int)  {
                clearButton.visibility = clearButtonVisibility(s)
                searchQuery = s.toString() // Обновляем searchQuery при изменении текста
            }
            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
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
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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
    /* Хранение данных START */
    // Переопределяем метод onSaveInstanceState (Сохранение состояния экземпляра),
    // чтобы сохранить текст из EditText (search_query) в Bundle методом putString
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, searchQuery)
    }
    private var restoredQuery:String = DEF_SEARCH
    // Переопределить метод onRestoreInstanceState(Восстановление состояния экземпляра),
    // чтобы достать данные из Bundle при помощи метода getString и установить их в EditText.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoredQuery = savedInstanceState.getString(KEY, DEF_SEARCH)
        if (restoredQuery != null) {
            searchQuery = restoredQuery
            val inputEditText = findViewById<EditText>(R.id.inputEditText)
            inputEditText.setText(searchQuery)
        }
    }
    /*
    private fun createTrackList(): List<Track> {
        return listOf(
            Track(
                "Smells Like Teen Spirit",
                "Nirvana",
                "5:01",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                "Billie Jean",
                "Michael Jackson",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            ),
            Track(
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                "Whole Lotta Love",
                "Led Zeppelin",
                "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
            ),
            Track(
                "Sweet Child O'Mine",
                "Guns N' Roses",
                "5:03",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
            )
        )
    }*/

    // В Kotlin для создания константной переменной мы используем companion object.
    // Ключ должен быть константным, чтобы мы точно знали, что он не изменится

    companion object {
        const val KEY = "search"
        const val DEF_SEARCH = ""
    }
    /* Хранение данных END */
}

