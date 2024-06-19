package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class StartSessionResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class ListenSession(

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("listen_id")
    val listenId: String? = null
)

data class Data(
    @field:SerializedName("listenSession")
    val listenSession: ListenSession? = null
)
