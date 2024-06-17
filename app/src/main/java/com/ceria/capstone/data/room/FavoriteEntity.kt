package com.ceria.capstone.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    var id: Int,
    var username: String,
    var album: String,
    var avatarurl: String
) : Parcelable
