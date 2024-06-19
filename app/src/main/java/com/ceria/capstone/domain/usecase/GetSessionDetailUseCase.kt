package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.SessionRepository

class GetSessionDetailUseCase(private val sessionRepository: SessionRepository) {
    suspend fun getSessionDetail(id: String) = sessionRepository.getSessionDetail(id)
}