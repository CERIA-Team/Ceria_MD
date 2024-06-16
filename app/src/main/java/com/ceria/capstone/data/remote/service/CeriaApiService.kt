package com.ceria.capstone.data.remote.service

import com.ceria.capstone.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CeriaApiService {
    @FormUrlEncoded
    @POST("/user")
    suspend fun auth(@Field("accessToken") accessToken: String): UserResponse
}