package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListeningSessionResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Count(

    @field:SerializedName("Session")
    val session: Int? = null
)

data class DataItem(

    @field:SerializedName("_count")
    val count: Count? = null,

    @field:SerializedName("listen_id")
    val listenId: String? = null
)
