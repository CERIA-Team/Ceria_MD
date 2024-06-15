package com.ceria.capstone.domain.usecase

import com.ceria.capstone.data.repository.UserRepository

class GetProfileUseCase(private val userRepository: UserRepository) {
    suspend fun getProfile() = userRepository.getProfile()
}