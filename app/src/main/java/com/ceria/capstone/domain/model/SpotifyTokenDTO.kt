package com.ceria.capstone.domain.model

import com.ceria.capstone.data.remote.response.SpotifyTokenResponse

data class SpotifyTokenDTO(
    val accessToken: String,
    val tokenType: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String
) {
    constructor(spotifyTokenResponse: SpotifyTokenResponse) : this(
        spotifyTokenResponse.accessToken,
        spotifyTokenResponse.tokenType,
        spotifyTokenResponse.scope,
        spotifyTokenResponse.expiresIn,
        spotifyTokenResponse.refreshToken
    )
}
