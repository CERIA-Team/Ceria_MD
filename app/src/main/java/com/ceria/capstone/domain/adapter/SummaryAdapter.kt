//package com.ceria.capstone.domain.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.ToggleButton
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.ceria.capstone.data.roomsummary.SummaryEntity
//import com.ceria.capstone.R
//import com.ceria.capstone.ui.liked.LikedViewModel
//import com.ceria.capstone.ui.summary.SummaryViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class SongAdapter(private var summaryEntities: List<SummaryEntity>, private val viewModel:SummaryViewModel) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
//        return SongViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
//        val summaryEntity = summaryEntities[position]
//        holder.bind(summaryEntity)
//    }
//
//    override fun getItemCount(): Int {
//        return summaryEntities.size
//    }
//
//    fun setSummaryEntities(entities: List<SummaryEntity>) {
//        summaryEntities = entities
//        notifyDataSetChanged()
//    }
//
//    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val nameTextView: TextView = itemView.findViewById(R.id.tv_item_name)
//        private val artistTextView: TextView = itemView.findViewById(R.id.tv_item_album)
//        private val imageView: ImageView = itemView.findViewById(R.id.img_item_photo)
//        private val toggle: ToggleButton = itemView.findViewById(R.id.toggle_favorite)
//
//        fun bind(summaryEntity: SummaryEntity) {
//            nameTextView.text = summaryEntity.albumNames
//            artistTextView.text = summaryEntity.artists
//
//            // Load the first image from the imageUrlList using Glide
//            val imageUrlList = summaryEntity.imageUrls.split(",")
//            if (imageUrlList.isNotEmpty()) {
//                Glide.with(itemView.context)
//                    .load(imageUrlList[0].trim()) // Add an error image if you have one
//                    .into(imageView)
//            }
//            var _isChecked = false
//            CoroutineScope(Dispatchers.IO).launch {
//                val count = viewModel.checkUser(summaryEntity.albumNames)
//                withContext(Dispatchers.Main) {
//                    if (count != null) {
//                        if (count > 0) {
//                            toggle.isChecked = true
//                            _isChecked = true
//                        } else {
//                            toggle.isChecked = false
//                            _isChecked = false
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//}
