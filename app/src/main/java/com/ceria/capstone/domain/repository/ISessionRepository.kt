package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result

interface ISessionRepository {
    suspend fun startSession(): LiveData<Result<String>>
    suspend fun getRecommendations(bpm: Int, listenId: String): LiveData<Result<List<String>>>
}