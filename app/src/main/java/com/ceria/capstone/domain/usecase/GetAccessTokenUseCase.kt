package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.AuthRepository

class GetAccessTokenUseCase(private val authRepository: AuthRepository) {
    suspend fun getAccessToken(code: String, redirectUri: String) =
        authRepository.getAccessToken(code, redirectUri)
}