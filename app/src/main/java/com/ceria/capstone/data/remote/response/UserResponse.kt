package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: User,
    @SerializedName("token") val token: String
)

data class User(
    @SerializedName("user_display_name") val displayName: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("user_email") val email: String
)