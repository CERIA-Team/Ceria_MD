package com.ceria.capstone.domain.model

import com.ceria.capstone.data.remote.response.QueueItem
import com.ceria.capstone.data.remote.response.TracksItem

data class SongDTO(
    val id: String,
    val title: String,
    val artist: String,
    val imageUrl: String
) {
    constructor(queueResponse: QueueItem) : this(
        id = queueResponse.id,
        title = queueResponse.name,
        artist = queueResponse.artists.map { it.name }.joinToString(", "),
        imageUrl = queueResponse.album.images?.firstOrNull()?.url ?: ""
    )

    constructor(track: TracksItem) : this(
        id = track.id,
        title = track.name,
        artist = track.artists.map { it.name }.joinToString(", "),
        imageUrl = track.album.images?.firstOrNull()?.url ?: ""
    )
}