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
import com.ceria.capstone.data.local.room.FavoriteEntity
import com.ceria.capstone.ui.liked.LikedViewModel
import android.widget.Filter
import android.widget.Filterable

//class LikedAdapter(
//    private var favoriteList: List<FavoriteEntity>,
//    private val likedViewModel: LikedViewModel,
//    private val onToggleFavorite: (FavoriteEntity) -> Unit
//) : RecyclerView.Adapter<LikedAdapter.ViewHolder>(), Filterable {
//
//    private var filteredFavoriteList: List<FavoriteEntity> = favoriteList
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imgItemPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
//        val tvItemAlbum: TextView = itemView.findViewById(R.id.tv_item_album)
//        val toggleFavorite: ToggleButton = itemView.findViewById(R.id.toggle_favorite)
//
//        fun bind(favorite: FavoriteEntity) {
//            tvItemName.text = favorite.title
//            tvItemAlbum.text = favorite.artist
//            Glide.with(itemView.context).load(favorite.imageUrl).into(imgItemPhoto)
//
//            toggleFavorite.isChecked = true
//
//            toggleFavorite.setOnClickListener {
//                onToggleFavorite(favorite)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_songs, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val favorite = filteredFavoriteList[position]
//        holder.bind(favorite)
//
//        holder.toggleFavorite.isChecked = true
//
//        holder.toggleFavorite.setOnClickListener {
//            likedViewModel.removeFavorite(favorite)
//        }
//    }
//
//    override fun getItemCount(): Int = filteredFavoriteList.size
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint?.toString() ?: ""
//                filteredFavoriteList = if (charString.isEmpty()) {
//                    favoriteList
//                } else {
//                    favoriteList.filter {
//                        it.username.contains(charString, ignoreCase = true)
//                    }
//                }
//                return FilterResults().apply { values = filteredFavoriteList }
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredFavoriteList = results?.values as List<FavoriteEntity>
//                notifyDataSetChanged()
//            }
//        }
//    }
//}
