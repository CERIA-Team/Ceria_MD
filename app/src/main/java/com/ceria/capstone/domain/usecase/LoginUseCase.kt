package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun authLogin(code: String, redirectUri: String) =
        authRepository.authLogin(code, redirectUri)
}