package com.ceria.capstone.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.data.roomsummary.SummaryEntity
import com.ceria.capstone.R

class SummaryAdapter(private var summaryEntities: List<SummaryEntity>) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
        return SummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val summaryEntity = summaryEntities[position]
        holder.bind(summaryEntity)
    }

    override fun getItemCount(): Int {
        return summaryEntities.size
    }

    fun setSummaryEntities(entities: List<SummaryEntity>) {
        summaryEntities = entities
        notifyDataSetChanged()
    }

    inner class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_item_name)
        private val artistTextView: TextView = itemView.findViewById(R.id.tv_item_album)
        private val imageView: ImageView = itemView.findViewById(R.id.img_item_photo)

        fun bind(summaryEntity: SummaryEntity) {
            nameTextView.text = summaryEntity.albumNames
            artistTextView.text = summaryEntity.artists

            // Load the first image from the imageUrlList using Glide
            val imageUrlList = summaryEntity.imageUrls.split(",")
            if (imageUrlList.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(imageUrlList[0].trim()) // Add an error image if you have one
                    .into(imageView)
            }
        }
    }
}
