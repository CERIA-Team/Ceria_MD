package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SpotifyTokenDTO

interface IAuthRepository {
    suspend fun checkToken(): LiveData<Result<String>>
    suspend fun authLogin(
        code: String,
        redirectUri: String
    ): LiveData<Result<SpotifyTokenDTO>>
    fun logout()
}