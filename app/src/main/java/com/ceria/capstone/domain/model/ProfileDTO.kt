package com.ceria.capstone.domain.model

import com.ceria.capstone.data.remote.response.ProfileResponse

data class ProfileDTO(
    val id: String,
    val displayName: String?,
    val email: String?,
    val followers: Int?,
    val imageUrl: String?,
    val product: String?
) {
    constructor(response: ProfileResponse) : this(
        id = response.id,
        displayName = response.displayName,
        email = response.email,
        followers = response.followers?.total,
        imageUrl = response.images?.lastOrNull()?.url,
        product = response.product
    )
}