package com.ceria.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.local.datastore.SessionManager
import com.ceria.capstone.data.remote.service.CeriaApiService
import com.ceria.capstone.data.remote.service.SpotifyAuthService
import com.ceria.capstone.domain.model.SpotifyTokenDTO
import com.ceria.capstone.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val spotifyAuthService: SpotifyAuthService,
    private val sessionManager: SessionManager,
    private val ceriaApiService: CeriaApiService
) : IAuthRepository {
    override suspend fun checkToken(): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        if (
            sessionManager.ceriaToken.first().isNullOrEmpty() ||
            sessionManager.spotifyAccessToken.first().isNullOrEmpty() ||
            sessionManager.spotifyRefreshToken.first().isNullOrEmpty()
        ) {
            sessionManager.removeToken()
            emit(Result.Empty)
            return@liveData
        } else {
            emit(Result.Success("Granted"))
        }
    }

    override suspend fun authLogin(
        code: String, redirectUri: String
    ): LiveData<Result<SpotifyTokenDTO>> = liveData {
        emit(Result.Loading)
        try {
            val spotifyAuthResponse = spotifyAuthService.getAccessToken(
                "authorization_code",
                code,
                redirectUri,
                BuildConfig.SPOTIFY_CLIENT_ID,
                BuildConfig.SPOTIFY_CLIENT_SECRET
            )
            val ceriaAuthResponse = ceriaApiService.auth(spotifyAuthResponse.accessToken)
            sessionManager.setCeriaToken(ceriaAuthResponse.token)
            sessionManager.setSpotifyAccessToken(spotifyAuthResponse.accessToken)
            sessionManager.setSpotifyRefreshToken(spotifyAuthResponse.refreshToken)
            emit(Result.Success(SpotifyTokenDTO(spotifyAuthResponse)))
        } catch (e: Exception) {
            Timber.e(e.message)
            emit(Result.Error("Failed to get access token"))
        }
    }

    override fun logout() {
        runBlocking {
            sessionManager.removeToken()
        }
    }
}