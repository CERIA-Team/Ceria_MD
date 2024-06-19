package com.ceria.capstone.data.remote.service

import com.ceria.capstone.data.remote.response.ListeningSessionResponse
import com.ceria.capstone.data.remote.response.StartSessionResponse
import com.ceria.capstone.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CeriaApiService {
    @FormUrlEncoded
    @POST("/user")
    suspend fun auth(@Field("accessToken") accessToken: String): UserResponse

    @POST("/listenSession")
    suspend fun startSession(): StartSessionResponse

    @GET("/listenSession")
    suspend fun getListeningSessions(): ListeningSessionResponse

    @FormUrlEncoded
    @POST("/recommend")
    suspend fun recommendSongs(
        @Field("bpm") bpm: Int,
        @Field("listen_id") listenId: String
    ): List<String>
}