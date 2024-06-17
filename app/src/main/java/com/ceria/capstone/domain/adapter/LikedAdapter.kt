package com.ceria.capstone.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.data.room.FavoriteEntity
import com.ceria.capstone.ui.liked.LikedViewModel

class LikedAdapter(
    private val favoriteList: List<FavoriteEntity>,
    private val likedViewModel: LikedViewModel,
    private val onToggleFavorite: (FavoriteEntity) -> Unit
) : RecyclerView.Adapter<LikedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItemPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvItemAlbum: TextView = itemView.findViewById(R.id.tv_item_album)
        val toggleFavorite: ToggleButton = itemView.findViewById(R.id.toggle_favorite)

        fun bind(favorite: FavoriteEntity) {
            tvItemName.text = favorite.username
            tvItemAlbum.text = favorite.album
            Glide.with(itemView.context).load(favorite.avatarurl).into(imgItemPhoto)

            toggleFavorite.isChecked = true

            toggleFavorite.setOnClickListener {
                onToggleFavorite(favorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = favoriteList[position]
        holder.bind(favorite)

        holder.toggleFavorite.isChecked = true

        holder.toggleFavorite.setOnClickListener {
            likedViewModel.removeFavorite(favorite)
        }
    }

    override fun getItemCount(): Int = favoriteList.size
}
