package com.ceria.capstone.domain.repository

import androidx.lifecycle.LiveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SessionDTO
import com.ceria.capstone.domain.model.SongDTO

interface ISessionRepository {
    suspend fun startSession(): LiveData<Result<String>>
    suspend fun getRecommendations(bpm: Int, listenId: String): LiveData<Result<List<String>>>
    suspend fun getNextQueue(): LiveData<Result<SongDTO>>
    suspend fun getSessionDetail(id: String): LiveData<Result<List<SongDTO>>>
    suspend fun getAllSessions(): LiveData<Result<List<SessionDTO>>>
    suspend fun addSongToFavorite(song: SongDTO)
    suspend fun removeSongFromFavorite(song: SongDTO)
    suspend fun getFavoriteSongs(): LiveData<Result<List<SongDTO>>>
    suspend fun checkFavorite(id: String): LiveData<Result<Boolean>>
}