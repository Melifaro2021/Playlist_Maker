package ru.makskim.playlist_maker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View

import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val linearLayout = findViewById<FrameLayout>(R.id.searchField)
        val inputEditText = findViewById<EditText>(R.id.input_edit_text)
        val xIco = findViewById<ImageView>(R.id.x_ico)

        xIco.setOnClickListener {
            inputEditText.setText("")
        }
        // логика по работе с введённым значением
        val simpleTextWatcher = object : TextWatcher {
            /** beforeTextChanged означает, что символы будут заменены новым теĸстом.
             * Теĸст не может быть изменён. Это событие используется, ĸогда нам
             * нужно взглянуть на старый теĸст, ĸоторый вот-вот изменится.**/

            /** Этот метод вызывается, чтобы уведомить вас о том,
             * что в пределах <code>s</code>:
             * символы <code>count</code>,
             * начинающиеся с <code>start</code>
             * будут заменены новым текстом длиной <code>after</code>.
             *
             * Попытка внести изменения в <code>s</code> из этот обратный вызов.**/
            override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }
            /** onTextChanged: внесены изменения, неĸоторые символы тольĸо что были заменены.
             * Теĸст не может быть изменён. Это событие используется, ĸогда нам нужно увидеть,
             * ĸаĸие символы в теĸсте являются новыми.**/
            override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int)  {
              xIco.visibility = clearButtonVisibility(s)
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
        val backButton = findViewById<ImageView>(R.id.arrBackSearchToMain)
        backButton.setOnClickListener {
            finish()
        }
    }
    /* Скрыть или показать крестик в поле поиска */
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}