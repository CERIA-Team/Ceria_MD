package com.ceria.capstone.utils

import com.ceria.capstone.data.remote.response.SpotifyTokenResponse
import com.ceria.capstone.domain.model.SpotifyTokenDTO

object DataMapper {
    fun SpotifyTokenResponse.mapToDomain() = SpotifyTokenDTO(
        accessToken,
        tokenType,
        scope,
        expiresIn,
        refreshToken
    )
}