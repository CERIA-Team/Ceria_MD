package com.ceria.capstone.domain.model

import com.ceria.capstone.data.local.room.FavoriteEntity
import com.ceria.capstone.data.remote.response.QueueItem
import com.ceria.capstone.data.remote.response.TracksItem

data class SongDTO(
    val id: String,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val isLiked: Boolean
) {
    constructor(queueResponse: QueueItem, liked: Boolean) : this(
        id = queueResponse.id,
        title = queueResponse.name,
        artist = queueResponse.artists.map { it.name }.joinToString(", "),
        imageUrl = queueResponse.album.images?.firstOrNull()?.url ?: "",
        isLiked = liked
    )

    constructor(track: TracksItem, liked: Boolean) : this(
        id = track.id,
        title = track.name,
        artist = track.artists.map { it.name }.joinToString(", "),
        imageUrl = track.album.images?.firstOrNull()?.url ?: "",
        isLiked = liked
    )

    constructor(favoriteEntity: FavoriteEntity) : this(
        id = favoriteEntity.id,
        title = favoriteEntity.title,
        artist = favoriteEntity.artist,
        imageUrl = favoriteEntity.imageUrl,
        isLiked = true
    )
}