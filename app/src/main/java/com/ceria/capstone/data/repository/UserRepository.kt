package com.ceria.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.remote.service.SpotifyApiService
import com.ceria.capstone.domain.model.ProfileDTO
import com.ceria.capstone.domain.repository.IUserRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val spotifyApiService: SpotifyApiService
) : IUserRepository {
    override suspend fun getProfile(): LiveData<Result<ProfileDTO>> = liveData {
        emit(Result.Loading)
        try {
            val profileResponse = spotifyApiService.getCurrentUserProfile()
            emit(Result.Success(ProfileDTO(profileResponse)))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

}
