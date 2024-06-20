package com.ceria.capstone.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.databinding.ItemSongsBinding
import com.ceria.capstone.domain.model.SongDTO
import java.util.Locale

class SongAdapter(
    private val addFavorite: (SongDTO) -> Unit,
    private val removeFavorite: (SongDTO) -> Unit
) : ListAdapter<SongDTO, SongAdapter.SongViewHolder>(DIFF_UTIL), Filterable {

    private var originalList: List<SongDTO> = listOf()
    private var filteredList: List<SongDTO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = filteredList[position]
        with(holder.binding) {
            tvItemName.text = item.title
            tvItemAlbum.text = item.artist
            Glide.with(root.context).load(item.imageUrl)
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.placeholder_song))
                .error(ContextCompat.getDrawable(root.context, R.drawable.placeholder_song))
                .into(imgItemPhoto)
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

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateList(list: List<SongDTO>) {
        originalList = list
        filteredList = list
        submitList(filteredList)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = if (constraint.isNullOrEmpty()) {
                    originalList
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                    originalList.filter {
                        it.title.lowercase(Locale.getDefault()).contains(filterPattern) ||
                                it.artist.lowercase(Locale.getDefault()).contains(filterPattern)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<SongDTO>
                submitList(filteredList)
            }
        }
    }
}
