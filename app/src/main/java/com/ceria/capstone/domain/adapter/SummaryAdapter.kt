package com.ceria.capstone.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ceria.capstone.data.roomsummary.SummaryEntity
import com.ceria.capstone.R

class SummaryAdapter(private var data: List<SummaryEntity>) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
        return SummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<SummaryEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_item_name)
        private val artistTextView: TextView = itemView.findViewById(R.id.tv_item_album)

        fun bind(summaryEntity: SummaryEntity) {
            nameTextView.text = summaryEntity.albumNames
            artistTextView.text = summaryEntity.artists

            // Example of handling imageUrls (splitting and loading images)
            val imageUrlList = summaryEntity.imageUrls.split(",")
            // Load images based on imageUrlList

            // Handle other UI updates as needed
        }
    }
}
