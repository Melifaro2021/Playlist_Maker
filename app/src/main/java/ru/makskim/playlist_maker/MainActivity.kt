package ru.makskim.playlist_maker
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка Поиск
        val searchButton = findViewById<Button>(R.id.search)
        searchButton.setOnClickListener {
            val goToSearch = Intent(
                this,
                SearchActivity::class.java)
            startActivity(goToSearch)
        }
        // Кнопка Медиатека
        val mediaButton = findViewById<Button>(R.id.media)
        mediaButton.setOnClickListener {
            val goToMedia = Intent(
                this,
                MediatekaActivity::class.java)
            startActivity(goToMedia)
        }
        /* Кнопка Настройки */
        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val goToSettings = Intent(
                this,
                SettingsActivity::class.java)
            startActivity(goToSettings)
        }
    }
}