package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository
import com.ceria.capstone.domain.model.SongDTO

class FavoriteUseCase(private val sessionRepository: SessionRepository) {
    suspend fun addSongToFavorite(song: SongDTO) {
        sessionRepository.addSongToFavorite(song)
    }

    suspend fun removeSongFromFavorite(song: SongDTO) {
        sessionRepository.removeSongFromFavorite(song)
    }

    suspend fun checkFavorite(id: String) = sessionRepository.checkFavorite(id)
}