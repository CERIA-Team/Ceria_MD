package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.ProfileDTO

interface IUserRepository {
    suspend fun getProfile(): LiveData<Result<ProfileDTO>>
}