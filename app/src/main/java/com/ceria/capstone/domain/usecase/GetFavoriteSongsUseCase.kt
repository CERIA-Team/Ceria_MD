package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository

class GetFavoriteSongsUseCase(private val sessionRepository: SessionRepository) {
    suspend fun getFavoriteSongs() = sessionRepository.getFavoriteSongs()
}