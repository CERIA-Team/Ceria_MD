package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    fun logout() = authRepository.logout()
}