package ru.makskim.playlist_maker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        /* Стрелка назад, вернуться на предыдущую страницу */
        initBackButton()
    }

    private fun initBackButton() {
        val backButton = findViewById<ImageView>(R.id.arrBackSearchToMain)
        backButton.setOnClickListener {
            finish()
        }
    }
}