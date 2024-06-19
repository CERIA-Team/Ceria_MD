package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository

class GetSongRecommendationsUseCase(private val sessionRepository: SessionRepository) {
    suspend fun getRecommendations(bpm: Int, userId: String) = sessionRepository.getRecommendations(bpm, userId)
}