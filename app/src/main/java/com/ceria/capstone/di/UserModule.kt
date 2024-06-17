package com.ceria.capstone.di

import com.ceria.capstone.data.repository.UserRepository
import com.ceria.capstone.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): IUserRepository
}