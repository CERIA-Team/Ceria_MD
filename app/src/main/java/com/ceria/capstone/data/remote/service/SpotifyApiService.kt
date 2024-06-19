package com.ceria.capstone.data.remote.service

import com.ceria.capstone.data.remote.response.ProfileResponse
import com.ceria.capstone.data.remote.response.QueueResponse
import com.ceria.capstone.data.remote.response.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApiService {
    @GET("me")
    suspend fun getCurrentUserProfile(): ProfileResponse

    @GET("me/player/queue")
    suspend fun getUserQueue(): QueueResponse

    @GET("tracks")
    suspend fun getTracks(
        @Query("ids") ids: String,
    ): TracksResponse
}