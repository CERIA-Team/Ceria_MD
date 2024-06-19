package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SongDTO

interface ISessionRepository {
    suspend fun startSession(): LiveData<Result<String>>
    suspend fun getRecommendations(bpm: Int, listenId: String): LiveData<Result<List<String>>>
    suspend fun getNextQueue(): LiveData<Result<SongDTO>>
    suspend fun getSessionDetail(id: String): LiveData<Result<List<SongDTO>>>
}