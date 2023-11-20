package ru.makskim.playlistMaker

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Стрелка назад переход с настроек на главную
        val arrToMain = findViewById<ImageView>(R.id.arrBack)
        arrToMain.setOnClickListener {
            finish()
        }

        // Кнопка поделиться приложением
        val shareButton = findViewById<FrameLayout>(R.id.shareButton)
        shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                val shareButtonText = resources.getString(R.string.shareButtonText)
                putExtra(Intent.EXTRA_TEXT, shareButtonText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        // Кнопка Написать в техподдержку
        val supportButton = findViewById<FrameLayout>(R.id.btn_support)
        supportButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            val supportText = resources.getString(R.string.text_mail)
            val supportTheme = resources.getString(R.string.theme_mail)
            shareIntent.data = Uri.parse("mailto:melifaro00@yandex.ru")

            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.email_name))

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, supportTheme)
            shareIntent.putExtra(Intent.EXTRA_TEXT, supportText)
            startActivity(shareIntent)
        }

        // Кнопка Пользовательское соглашение
        val termsButton = findViewById<FrameLayout>(R.id.btn_terms)
        termsButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.terms_article)))
            try{
                startActivity(shareIntent)
            } catch (e:Exception){
                Log.d(ContentValues.TAG, "No Intent available to handle action")
                // Сообщение для пользователя
                Toast.makeText(this, "Не найдено приложение для открытия ссылки", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
