package com.ceria.capstone.domain.model

data class SpotifyToken(
    val accessToken: String,
    val tokenType: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String
)
