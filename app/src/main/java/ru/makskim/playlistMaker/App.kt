package ru.makskim.playlistMaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val NIGHT_THEME_SHARED_PREF = "theme_mode" // NIGHT_THEME_SHARED_PREF название файла, в который сохраняется значение
const val STATUS_NIGHT_THEME = "status_night_theme"

class App : Application() {
    private var darkTheme: Boolean = false

    override fun onCreate() {
        super.onCreate()

        // первый параметр — NIGHT_THEME_SHARED_PREF название файла, в который сохраняется значение
        // второй параметр — MODE_PRIVATE отвечает за доступность сохранённых данных.
        val sharedPrefs = getSharedPreferences(NIGHT_THEME_SHARED_PREF, MODE_PRIVATE)

        // Создадим экземпляр SharedPreferences и константу ключа для сохраняемого нами текста:
        darkTheme = sharedPrefs.getBoolean(STATUS_NIGHT_THEME, false)
        switchTheme(darkTheme)
    }

    /**
     * в индикатор darkTheme задаем значение из полученного параметра и
     * устанавливаем нужную тему с помощью класса AppCompatDelegate и
     * его метода setDefaultNightMode
     * (см. пункт 8 урока Спринт 12 → Тема 1: Shared Preferences → Урок 3)
     */
    fun switchTheme(darkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
