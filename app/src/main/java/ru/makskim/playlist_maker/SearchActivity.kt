package ru.makskim.playlist_maker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    private lateinit var search_query: String
    private lateinit var searchField: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val linearLayout = findViewById<FrameLayout>(R.id.search_field)
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        val xIco = findViewById<ImageView>(R.id.clear_search_field_icon)

        xIco.setOnClickListener {
            inputEditText.setText("")
        }

        /* Кнопка крестика , чтобы стереть поисковый запрос */
        initClearButton()

        // логика по работе с введённым значением
        val simpleTextWatcher = object : TextWatcher {
            /** beforeTextChanged означает, что символы будут заменены новым теĸстом.
             * Теĸст не может быть изменён. Это событие используется, ĸогда нам
             * нужно взглянуть на старый теĸст, ĸоторый вот-вот изменится.**/

            /** Этот метод вызывается, чтобы уведомить вас о том,
             * что в пределах <code>s</code>:
             * символы <code>count</code>,
             * начинающиеся с <code>start</code>
             * будут заменены новым текстом длиной <code>after</code>. **/
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }
            /** onTextChanged: внесены изменения, неĸоторые символы тольĸо что были заменены.
             * Теĸст не может быть изменён. Это событие используется, ĸогда нам нужно увидеть,
             * ĸаĸие символы в теĸсте являются новыми.**/
            override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int)  {
              xIco.visibility = clearButtonVisibility(s)
                search_query = s.toString() // Обновляем searchQuery при изменении текста
            }
            /** afterTextChanged: внесены изменения, но теĸст доступен для редаĸтирования.
             * Это событие используется, ĸогда нам нужно увидеть и, возможно,
             * отредаĸтировать новый теĸст. **/
            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        /* Стрелка назад , вызов */
        initBackButton()
    }
    /* Стрелка назад, вернуться на предыдущую страницу */
    private fun initBackButton() {
        val backButton = findViewById<ImageView>(R.id.arr_back_search_to_main)
        backButton.setOnClickListener {
            finish()
        }
    }
    // ф-ЦИЯ УДАЛЕНИЯ КЛАВИАТУРЫ НА КРЕСТИК
    private fun initClearButton(): ImageView? {
        val clearButton = findViewById<ImageView>(R.id.clear_search_field_icon)
        clearButton.setOnClickListener {
            searchField.setText("")
            /*InputMethodManager — вспомогательный класс, который выступает в роли посредника между
            источником ввода и приложением. Код ниже может скрыть клавиатуру:*/
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchField.windowToken, 0)
        }
        return clearButton
    }
    /* Скрыть или показать крестик в поле поиска */
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    /* Хранение данных START */
    // search_query — это переменная, которую нам нужно сохранить.
    // KEY — специальный ключ, по которому мы будем сохранять и восстанавливать наше значение.
    // DEF_SEARCH — это значение по умолчанию для переменной search_query

    private var restored_query:String = DEF_SEARCH

    // Переопределяем метод onSaveInstanceState (Сохранение состояния экземпляра),
    // чтобы сохранить текст из EditText (search_query) в Bundle методом putString
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, search_query)
    }
    // Переопределить метод onRestoreInstanceState(Восстановление состояния экземпляра),
    // чтобы достать данные из Bundle при помощи метода getString и установить их в EditText.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restored_query = savedInstanceState.getString(KEY, DEF_SEARCH)
    }

    // В Kotlin для создания константной переменной мы используем companion object.
    // Ключ должен быть константным, чтобы мы точно знали, что он не изменится
    companion object {
        const val KEY = "search"
        const val DEF_SEARCH = ""
    }
    /* Хранение данных END */
}