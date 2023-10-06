package ru.makskim.playlist_maker
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка Поиск
        val searchButton = findViewById<Button>(R.id.search)
        searchButton.setOnClickListener {
            val displayIntent = Intent(
                this,
                SearchActivity::class.java)
            startActivity(displayIntent)
        }
        // Кнопка Медиатека
        val mediaButton = findViewById<Button>(R.id.media)
        mediaButton.setOnClickListener {
            val displayIntent2 = Intent(
                this,
                MediatekaActivity::class.java)
            startActivity(displayIntent2)
        }
        /* Кнопка Настройки 1*/
        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val displayIntent3 = Intent(
                this,
                SettingsActivity::class.java)
            startActivity(displayIntent3)
        }

        /* Кнопка Настройки 2 */
        /* TOAST Способ 1. Реализация анонимного класса Start
        val settingsButton = findViewById<Button>(R.id.settings)
        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Нажали кнопку Настройки",
                        Toast.LENGTH_SHORT).show()
            }
        }
        settingsButton.setOnClickListener(imageClickListener)*/
        //TOAST Способ 1. Реализация анонимного класса END

        /* Кнопка Настройки 3
        // TOAST Способ 2. Лямбда-выражение Start
        settingsButton.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "Настройки!",
                Toast.LENGTH_SHORT).show()
        }*/
        // TOAST Способ 2. Лямбда-выражение END
    }
}