package com.ceria.capstone.di

import android.content.Context
import androidx.room.Room
import com.ceria.capstone.data.local.datastore.SessionManager
import com.ceria.capstone.data.local.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext, FavoriteDatabase::class.java, "favorite_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}