package com.ceria.capstone.di

import com.ceria.capstone.data.repository.AuthRepository
import com.ceria.capstone.data.repository.SessionRepository
import com.ceria.capstone.data.repository.UserRepository
import com.ceria.capstone.domain.usecase.CheckTokenUseCase
import com.ceria.capstone.domain.usecase.GetProfileUseCase
import com.ceria.capstone.domain.usecase.GetSongRecommendationsUseCase
import com.ceria.capstone.domain.usecase.LoginUseCase
import com.ceria.capstone.domain.usecase.LogoutUseCase
import com.ceria.capstone.domain.usecase.StartSessionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCheckTokenUseCase(authRepository: AuthRepository): CheckTokenUseCase {
        return CheckTokenUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetProfileUseCase(userRepository: UserRepository): GetProfileUseCase {
        return GetProfileUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideStartSessionUseCase(sessionRepository: SessionRepository): StartSessionUseCase {
        return StartSessionUseCase(sessionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetSongRecommndationsUseCase(sessionRepository: SessionRepository): GetSongRecommendationsUseCase {
        return GetSongRecommendationsUseCase(sessionRepository)
    }
}