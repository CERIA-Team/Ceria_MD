package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository

class GetNextQueueUseCase(private val sessionRepository: SessionRepository) {
    suspend fun getNextQueue() = sessionRepository.getNextQueue()
}