package com.ceria.capstone.data.remote.interceptor

import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.local.SessionManager
import com.ceria.capstone.data.remote.service.SpotifyAuthService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SpotifyTokenInterceptor(
    private val authService: SpotifyAuthService, private val sessionManager: SessionManager
) : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val currentToken = runBlocking {
            sessionManager.spotifyAccessToken.firstOrNull()
        }
        val authenticatedRequest = if (!currentToken.isNullOrEmpty()) {
            request.newBuilder().header("Authorization", "Bearer $currentToken").build()
        } else {
            request
        }
        var response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            val refreshToken = runBlocking {
                sessionManager.spotifyRefreshToken.firstOrNull()
            }

            if (!refreshToken.isNullOrEmpty()) {
                try {
                    val refreshTokenResponse = runBlocking {
                        authService.refreshAccessToken(
                            grantType = "refresh_token",
                            refreshToken = refreshToken,
                            clientId = BuildConfig.SPOTIFY_CLIENT_ID,
                            clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET
                        )
                    }

                    val newAccessToken = refreshTokenResponse.accessToken

                    val newRequest =
                        request.newBuilder().header("Authorization", "Bearer $newAccessToken")
                            .build()

                    response.close()
                    response = chain.proceed(newRequest)

                    runBlocking {
                        sessionManager.setSpotifyAccessToken(newAccessToken)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return response
    }
}