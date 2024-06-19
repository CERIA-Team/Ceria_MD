package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class QueueResponse(
    @field:SerializedName("currently_playing") val currentlyPlaying: CurrentlyPlaying? = null,

    @field:SerializedName("queue") val queue: List<QueueItem>
)

data class ImagesItem(

    @field:SerializedName("width") val width: Int? = null,

    @field:SerializedName("url") val url: String? = null,

    @field:SerializedName("height") val height: Int? = null
)

data class QueueItem(

    @field:SerializedName("album") val album: Album,
    @field:SerializedName("artists") val artists: List<ArtistsItem>,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("id") val id: String,
)

data class ArtistsItem(
    @field:SerializedName("name") val name: String? = null,
)

data class CurrentlyPlaying(

    @field:SerializedName("album") val album: Album? = null,
    @field:SerializedName("artists") val artists: List<ArtistsItem?>? = null,
    @field:SerializedName("name") val name: String? = null,
)

data class Album(

    @field:SerializedName("images") val images: List<ImagesItem?>? = null,

    @field:SerializedName("name") val name: String? = null,
)
