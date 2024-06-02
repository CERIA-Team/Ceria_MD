package com.ceria.capstone.di

import com.ceria.capstone.data.repository.AuthRepository
import com.ceria.capstone.domain.repository.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepository): IAuthRepository
}