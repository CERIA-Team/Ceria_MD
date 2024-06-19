package com.ceria.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.remote.service.CeriaApiService
import com.ceria.capstone.domain.repository.ISessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val ceriaApi: CeriaApiService
) : ISessionRepository {
    override suspend fun startSession(): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = ceriaApi.startSession()
            if (response.data?.listenSession?.listenId != null) {
                emit(Result.Success(response.data.listenSession.listenId))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun getRecommendations(
        bpm: Int,
        listenId: String
    ): LiveData<Result<List<String>>> = liveData {
        emit(Result.Loading)
        try {
            val response = ceriaApi.recommendSongs(bpm, listenId)
            if (response.isNotEmpty()) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error("No songs found"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}