package com.ceria.capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ceria.capstone.R
import com.ceria.capstone.databinding.ItemSessionBinding
import com.ceria.capstone.domain.model.SessionDTO

class SessionAdapter(private val onClickDetail: (String) -> Unit) :
    ListAdapter<SessionDTO, SessionAdapter.SessionViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvSessionId.text = root.context.getString(R.string.session_id, item.id)
            tvNSongs.text = root.context.getString(R.string.played_n_songs, item.songCount)
            tvViewDetails.setOnClickListener {
                onClickDetail.invoke(item.id)
            }
        }
    }

    inner class SessionViewHolder(val binding: ItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SessionDTO>() {
            override fun areItemsTheSame(oldItem: SessionDTO, newItem: SessionDTO): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SessionDTO, newItem: SessionDTO): Boolean {
                return oldItem == newItem
            }
        }
    }
}