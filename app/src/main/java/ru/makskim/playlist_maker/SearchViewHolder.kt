package ru.makskim.playlist_maker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val songName = itemView.findViewById<TextView>(R.id.song_name) // Название композиции
    private val artistName = itemView.findViewById<TextView>(R.id.artist_name) // Имя исполнителя
    private val songTime = itemView.findViewById<TextView>(R.id.song_time) // Продолжительность трека
    private val icoSong = itemView.findViewById<ImageView>(R.id.ico_song) // Ссылка на изображение обложки
    //см. имена переменных в Track.kt
    fun bind(item: Track) {
        songName.text = item.trackName // Имя исполнителя
        artistName.text = item.artistName // Название композиции
        songTime.text = item.trackTime // Продолжительность трека
        // Ссылка на изображение обложки
        Glide.with(itemView.context)
            .load(item.artworkUrl100)
            .transform(RoundedCorners(8))
            .fitCenter()
            .placeholder(R.drawable.filter_24)
            .error(R.drawable.ico_arr_back)
            .into(icoSong)
    }
}