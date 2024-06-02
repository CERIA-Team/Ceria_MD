package com.ceria.capstone.utils

import com.ceria.capstone.data.remote.response.SpotifyTokenResponse
import com.ceria.capstone.domain.model.SpotifyToken

object DataMapper {
    fun mapSpotifyTokenResponseToDomain(input: SpotifyTokenResponse) =
        SpotifyToken(
            input.accessToken,
            input.tokenType,
            input.scope,
            input.expiresIn,
            input.refreshToken
        )
}