package ru.makskim.playlistMaker

//Ответ от сервера имеет следующий формат
data class SearchResult(
    val resultCount: Int,
    val results: List<Track>
)

data class Track(
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTimeMillis: Int, // Продолжительность трека
    val artworkUrl100: String // Ссылка на изображение обложки
)