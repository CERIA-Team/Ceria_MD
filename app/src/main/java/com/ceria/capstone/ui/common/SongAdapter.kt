package com.ceria.capstone.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.databinding.ItemSongsBinding
import com.ceria.capstone.domain.model.SongDTO

class SongAdapter(
    private val addFavorite: (SongDTO) -> Unit,
    private val removeFavorite: (SongDTO) -> Unit
) :
    ListAdapter<SongDTO, SongAdapter.SongViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvItemName.text = item.title
            tvItemAlbum.text = item.artist
            Glide.with(root.context).load(
                item.imageUrl
            ).placeholder(
                ContextCompat.getDrawable(
                    root.context, R.drawable.placeholder_song
                )
            ).error(
                ContextCompat.getDrawable(
                    root.context, R.drawable.placeholder_song
                )
            ).into(imgItemPhoto)
            toggleFavorite.setOnCheckedChangeListener(null)
            toggleFavorite.isChecked = item.isLiked
            toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addFavorite(item)
                } else {
                    removeFavorite(item)
                }
            }
        }
    }

    inner class SongViewHolder(val binding: ItemSongsBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SongDTO>() {
            override fun areItemsTheSame(oldItem: SongDTO, newItem: SongDTO): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SongDTO, newItem: SongDTO): Boolean {
                return oldItem == newItem
            }
        }
    }
}