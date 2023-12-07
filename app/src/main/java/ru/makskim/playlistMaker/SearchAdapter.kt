package ru.makskim.playlistMaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter: RecyclerView.Adapter<SearchViewHolder>(){
    var tracks = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_song, parent, false)
        return SearchViewHolder(view)
    }
    override fun getItemCount(): Int {
        return tracks.size
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(tracks[position])
    }
}