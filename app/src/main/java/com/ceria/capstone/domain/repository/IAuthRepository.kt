package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SpotifyToken

interface IAuthRepository {
    suspend fun getAccessToken(
        code: String,
        redirectUri: String
    ): LiveData<Result<SpotifyToken>>
}