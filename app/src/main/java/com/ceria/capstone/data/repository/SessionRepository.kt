package com.ceria.capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.local.room.FavoriteDatabase
import com.ceria.capstone.data.local.room.FavoriteEntity
import com.ceria.capstone.data.remote.service.CeriaApiService
import com.ceria.capstone.data.remote.service.SpotifyApiService
import com.ceria.capstone.domain.model.SessionDTO
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.repository.ISessionRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val ceriaApiService: CeriaApiService,
    private val spotifyApiService: SpotifyApiService,
    private val favoriteDatabase: FavoriteDatabase
) : ISessionRepository {
    override suspend fun startSession(): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = ceriaApiService.startSession()
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
        bpm: Int, listenId: String
    ): LiveData<Result<List<String>>> = liveData {
        emit(Result.Loading)
        try {
            val response = ceriaApiService.recommendSongs(bpm, listenId)
            if (response.isNotEmpty()) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error("No songs found"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun getSessionDetail(id: String): LiveData<Result<List<SongDTO>>> = liveData {
        emit(Result.Loading)
        try {
            val ceriaResponse = ceriaApiService.getSessionDetail(id)
            if (ceriaResponse.data != null && ceriaResponse.data.sessions.isNotEmpty()) {
                val songIds = ceriaResponse.data.sessions.joinToString(",") {
                    it.songId
                }
                val spotifyResponse = spotifyApiService.getTracks(songIds)
                val songs = spotifyResponse.tracks.map {
                    val fav = favoriteDatabase.favoriteDao().isSongFavorite(it.id)
                    SongDTO(it, fav)
                }
                emit(Result.Success(songs))
            } else {
                emit(Result.Empty)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun getAllSessions(): LiveData<Result<List<SessionDTO>>> = liveData {
        emit(Result.Loading)
        try {
            val response = ceriaApiService.getListeningSessions()
            if (!response.data.isNullOrEmpty()) {
                val sessions = response.data.map {
                    SessionDTO(it)
                }
                emit(Result.Success(sessions))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    override suspend fun getNextQueue(): LiveData<Result<SongDTO>> = liveData {
        emit(Result.Loading)
        try {
            val response = spotifyApiService.getUserQueue()
            if (response.queue.isEmpty()) {
                emit(Result.Empty)
            } else {
                val isLiked = favoriteDatabase.favoriteDao().isSongFavorite(response.queue[0].id)
                val song = SongDTO(response.queue[0], isLiked)
                emit(Result.Success(song))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun addSongToFavorite(song: SongDTO) {
        try {
            favoriteDatabase.favoriteDao().addSongToFavorite(FavoriteEntity(song))
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    override suspend fun removeSongFromFavorite(song: SongDTO) {
        try {
            favoriteDatabase.favoriteDao().removeSongFromFavorite(song.id)
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }

    override suspend fun getFavoriteSongs(): LiveData<Result<List<SongDTO>>> = liveData {
        emit(Result.Loading)
        try {
            val songs = favoriteDatabase.favoriteDao().getFavoriteSongs().map {
                SongDTO(it)
            }
            emit(Result.Success(songs))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}