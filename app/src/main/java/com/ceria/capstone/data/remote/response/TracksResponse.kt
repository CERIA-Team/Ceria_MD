package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName


data class TracksResponse(

    @field:SerializedName("tracks")
    val tracks: List<TracksItem>
)
data class TracksItem(
    @field:SerializedName("album") val album: Album,
    @field:SerializedName("artists") val artists: List<ArtistsItem>,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("id") val id: String
)




