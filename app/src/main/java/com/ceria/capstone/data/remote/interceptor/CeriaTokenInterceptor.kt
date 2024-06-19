package com.ceria.capstone.data.remote.interceptor

import com.ceria.capstone.data.local.SessionManager
import com.ceria.capstone.data.remote.service.SpotifyAuthService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CeriaTokenInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val currentToken = runBlocking {
            sessionManager.ceriaToken.firstOrNull()
        }
        val authenticatedRequest = if (!currentToken.isNullOrEmpty()) {
            request.newBuilder().header("Authorization", "Bearer $currentToken").build()
        } else {
            request
        }
        val response = chain.proceed(authenticatedRequest)
        return response
    }
}