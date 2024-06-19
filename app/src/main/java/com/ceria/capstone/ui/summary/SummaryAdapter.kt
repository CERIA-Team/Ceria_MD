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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummaryAdapter(private val viewModel: SummaryViewModel) : ListAdapter<SongDTO, SummaryAdapter.SummaryViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val binding = RecycleviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SummaryViewHolder(private val binding: RecycleviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SongDTO) {
            with(binding) {
                tvItemName.text = item.title
                tvItemAlbum.text = item.artist
                Glide.with(root.context).load(item.imageUrl)
                    .placeholder(ContextCompat.getDrawable(root.context, R.drawable.placeholder_song))
                    .error(ContextCompat.getDrawable(root.context, R.drawable.placeholder_song))
                    .into(imgItemPhoto)

                // Set initial favorite state
                CoroutineScope(Dispatchers.IO).launch {
                    val count = viewModel.checkUser(item.artist)
                    withContext(Dispatchers.Main) {
                        toggleFavorite.isChecked = count > 0
                    }
                }

                // Handle toggle favorite button click
                toggleFavorite.setOnClickListener {
                    if (toggleFavorite.isChecked) {
                        viewModel.insertFavorite(item.title,itemId.toInt(), item.artist, item.imageUrl)
                    } else {
                        viewModel.removeFavorite(itemId.toInt())
                    }
                }
            }
        }
    }

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
