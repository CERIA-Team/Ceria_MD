package com.ceria.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    val country: String?,
    @SerializedName("display_name") val displayName: String?,
    val email: String?,
    @SerializedName("explicit_content") val explicitContent: ExplicitContent?,
    @SerializedName("external_urls") val externalUrls: ExternalUrls?,
    val followers: Followers?,
    val href: String,
    val id: String,
    val images: List<Image>?,
    val product: String?,
    val type: String?,
    val uri: String?
)

data class ExplicitContent(
    @SerializedName("filter_enabled") val filterEnabled: Boolean,
    @SerializedName("filter_locked") val filterLocked: Boolean
)

data class ExternalUrls(
    val spotify: String?
)

data class Followers(
    val href: String?,
    val total: Int
)

data class Image(
    val url: String?,
    val height: Int,
    val width: Int
)