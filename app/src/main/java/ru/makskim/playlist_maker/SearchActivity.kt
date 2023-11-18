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
    private lateinit var inputEditText: EditText // 1. Строка ввода текста в поле поиска
    private lateinit var searchQuery: String // 2. Поле ввода текста
    private lateinit var searchField: ConstraintLayout // 3. Поле поиска
    private lateinit var rvSearchSongs: RecyclerView // 4. Определим RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        rvSearchSongs = findViewById(R.id.rv_search_songs) // 4. Определим RecyclerView

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

    // В Kotlin для создания константной переменной мы используем companion object.
    // Ключ должен быть константным, чтобы мы точно знали, что он не изменится

    companion object {
        const val KEY = "search"
        const val DEF_SEARCH = ""
    }
    /* Хранение данных END */
}