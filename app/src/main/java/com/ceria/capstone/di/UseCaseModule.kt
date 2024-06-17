package com.ceria.capstone.di

import android.media.tv.TvContract.Channels.Logo
import com.ceria.capstone.data.repository.AuthRepository
import com.ceria.capstone.data.repository.UserRepository
import com.ceria.capstone.domain.usecase.CheckTokenUseCase
import com.ceria.capstone.domain.usecase.GetProfileUseCase
import com.ceria.capstone.domain.usecase.LoginUseCase
import com.ceria.capstone.domain.usecase.LogoutUseCase
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
}