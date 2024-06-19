package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository

class StartSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend fun startSession() = sessionRepository.startSession()
}