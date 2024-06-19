package com.ceria.capstone.data.local.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ceria.capstone.domain.model.SongDTO
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_songs")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val artist: String,
    val imageUrl: String
) : Parcelable {
    constructor(songDTO: SongDTO) : this(
        id = songDTO.id,
        title = songDTO.title,
        artist = songDTO.artist,
        imageUrl = songDTO.imageUrl
    )
}
