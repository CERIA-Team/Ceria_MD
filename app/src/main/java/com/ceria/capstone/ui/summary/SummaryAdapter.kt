package com.ceria.capstone.ui.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.databinding.RecycleviewBinding
import com.ceria.capstone.domain.model.SongDTO

class SummaryAdapter() : ListAdapter<SongDTO, SummaryAdapter.SummaryViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val binding = RecycleviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
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
        }
    }

    inner class SummaryViewHolder(val binding: RecycleviewBinding) :
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