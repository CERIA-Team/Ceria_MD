import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceria.capstone.R // Change R to your actual resource file
import com.ceria.capstone.data.song.Song


class AddSongAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<AddSongAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistName: TextView = itemView.findViewById(R.id.tv_item_name)
        val albumName: TextView = itemView.findViewById(R.id.tv_item_album)
        val albumImage: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycleview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = songList[position]
        holder.artistName.text = currentItem.artistName
        holder.albumName.text = currentItem.albumName
        Glide.with(holder.itemView.context)
            .load(currentItem.albumImageUrl)
            .into(holder.albumImage)
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}
