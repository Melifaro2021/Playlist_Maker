package ru.makskim.playlist_maker
import android.annotation.SuppressLint
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
        /* Способ 1. Лямбда-выражение */
        /* 1. Добавление ссылки (на View из XML) в переменной image*/
        val btnsearch = findViewById<Button>(R.id.search)
        btnsearch.setOnClickListener {
            Toast.makeText(this@MainActivity,
                "Нажали на кнопку поиск!",
                Toast.LENGTH_SHORT).show()
        }
        val btnmedia = findViewById<Button>(R.id.media)
        btnmedia.setOnClickListener {
            Toast.makeText(
                this@MainActivity,"Нажали на кнопку медиатека!", Toast.LENGTH_SHORT).show()
        }
        /* Способ 2. Реализация анонимного класса  */
        /* 1. Добавление ссылки*/
        val btnsettings = findViewById<Button>(R.id.settings)
        /*2. Добавление слушателя */
        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                /* 4. Добавление системной надписи, 5.Не забыли Show! */
                Toast.makeText(
                    this@MainActivity,
                    "Нажали на кнопку настройки!",
                    Toast.LENGTH_SHORT).show()
            }
        }
        /*3. Соединить ссылку на кнопку и слушатель нажатия*/
        btnsettings.setOnClickListener(imageClickListener)

        /*val btnsettings = findViewById<Button>(R.id.settings)
        btnsettings.setOnClickListener {
            Toast.makeText(this@MainActivity,"Нажали на кнопку настройки!",Toast.LENGTH_SHORT).show()
        }*/

        /* Работа с ImageView из кода  */
        /* установить цвет фона image.setBackgroundColor(getColor(R.color.green))*/
        /* установить тип масштабирования image.scaleType = ImageView.ScaleType.CENTER_CROP*/
        /* установить в ImageView изображение из ресурсов image.setImageResource(R.drawable.poster)*/

    }

}