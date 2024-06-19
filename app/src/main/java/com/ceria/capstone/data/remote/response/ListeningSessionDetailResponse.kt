package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListeningSessionDetailResponse(

    @field:SerializedName("data") val data: DetailData? = null,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("status") val status: Boolean? = null
)

data class SessionsItem(

    @field:SerializedName("song_id") val songId: String,

    @field:SerializedName("listen_id") val listenId: String,

    @field:SerializedName("createAt") val createAt: String? = null
)

data class DetailData(

    @field:SerializedName("sessions") val sessions: List<SessionsItem>
)
