package com.ceria.capstone.di

import com.ceria.capstone.data.repository.SessionRepository
import com.ceria.capstone.domain.repository.ISessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {
    @Binds
    abstract fun bindSessionRepository(sessionRepository: SessionRepository): ISessionRepository
}