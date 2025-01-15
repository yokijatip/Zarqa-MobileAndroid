package com.gity.myzarqa.di

import com.gity.myzarqa.data.remote.api.ApiService
import com.gity.myzarqa.data.repository.AuthRepositoryImpl
import com.gity.myzarqa.domain.repository.AuthRepository
import com.gity.myzarqa.helper.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService, // <- Dari NetworkModule
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, sessionManager)
    }
}