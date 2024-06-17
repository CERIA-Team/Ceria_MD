package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.AuthRepository

class CheckTokenUseCase(private val authRepository: AuthRepository) {
    suspend fun checkToken() = authRepository.checkToken()
}