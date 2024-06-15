package com.ceria.capstone.data.remote.service

import com.ceria.capstone.data.remote.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface SpotifyApiService {
    @GET("me")
    suspend fun getCurrentUserProfile() : ProfileResponse
}