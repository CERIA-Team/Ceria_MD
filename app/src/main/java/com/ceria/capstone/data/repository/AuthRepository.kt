package com.ceria.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.remote.service.SpotifyAuthService
import com.ceria.capstone.domain.model.SpotifyToken
import com.ceria.capstone.domain.repository.IAuthRepository
import com.ceria.capstone.utils.DataMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val spotifyAuthService: SpotifyAuthService
) : IAuthRepository {
    override suspend fun getAccessToken(
        code: String,
        redirectUri: String
    ): LiveData<Result<SpotifyToken>> = liveData {
        emit(Result.Loading)
        try {
            val response = spotifyAuthService.getAccessToken(
                "authorization_code",
                code,
                redirectUri,
                BuildConfig.SPOTIFY_CLIENT_ID,
                BuildConfig.SPOTIFY_CLIENT_SECRET
            )
            emit(Result.Success(DataMapper.mapSpotifyTokenResponseToDomain(response)))
        } catch (e: Exception) {
            emit(Result.Error("Failed to get access token"))
        }
    }
}