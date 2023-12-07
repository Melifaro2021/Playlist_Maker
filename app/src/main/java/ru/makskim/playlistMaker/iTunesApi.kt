package ru.makskim.playlistMaker

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val iTunesUrl = "https://itunes.apple.com/" // Подключение API
// Инициализация Retrofit \
private val retrofit = Retrofit.Builder()
    .baseUrl(iTunesUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val iTunesService: ITunesApi = retrofit.create(ITunesApi::class.java) // Создание экземпляра ITunesApi

// получить список песен. Для этого нужен интерфейс с функцией поиска
interface ITunesApi {
    @GET("/search?entity=song") //Для поиска песен используем следующий GET-запрос
    fun search(@Query("term") text: String) : Call<SearchResult> //Текст для поиска передаётся в @Query параметром term
}