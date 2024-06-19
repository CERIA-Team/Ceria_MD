package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository


class GetAllSessionsUseCase(private val sessionRepository: SessionRepository) {
    suspend fun getAllSessions() = sessionRepository.getAllSessions()
}