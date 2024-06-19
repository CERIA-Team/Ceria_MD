package com.ceria.capstone.di

import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.local.SessionManager
import com.ceria.capstone.data.remote.interceptor.CeriaTokenInterceptor
import com.ceria.capstone.data.remote.interceptor.SpotifyTokenInterceptor
import com.ceria.capstone.data.remote.service.CeriaApiService
import com.ceria.capstone.data.remote.service.SpotifyApiService
import com.ceria.capstone.data.remote.service.SpotifyAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideSpotifyTokenInterceptor(
        authService: SpotifyAuthService, sessionManager: SessionManager
    ): SpotifyTokenInterceptor {
        return SpotifyTokenInterceptor(authService, sessionManager)
    }

    @Singleton
    @Provides
    fun provideCeriaTokenInterceptor(
        sessionManager: SessionManager
    ): CeriaTokenInterceptor {
        return CeriaTokenInterceptor(sessionManager)
    }

    @Singleton
    @Provides
    fun provideSpotifyAuthApi(client: OkHttpClient): SpotifyAuthService {
        return Retrofit.Builder().baseUrl(BuildConfig.SPOTIFY_AUTH_BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SpotifyAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideSpotifyApi(
        client: OkHttpClient, spotifyTokenInterceptor: SpotifyTokenInterceptor
    ): SpotifyApiService {
        val httpClient = client.newBuilder().addInterceptor(spotifyTokenInterceptor).build()
        return Retrofit.Builder().baseUrl(BuildConfig.SPOTIFY_API_BASE_URL).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SpotifyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCeriaApi(
        client: OkHttpClient,
        ceriaTokenInterceptor: CeriaTokenInterceptor
    ): CeriaApiService {
        val httpClient = client.newBuilder().addInterceptor(ceriaTokenInterceptor).build()
        return Retrofit.Builder().baseUrl(BuildConfig.CERIA_API_BASE_URL).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CeriaApiService::class.java)
    }
}