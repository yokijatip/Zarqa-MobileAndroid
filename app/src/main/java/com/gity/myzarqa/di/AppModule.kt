package com.gity.myzarqa.di

import android.content.Context
import com.gity.myzarqa.utils.Engine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Scope untuk module
object AppModule {
//    Membuat Blueprint untuk membuat Dependency Injection

    @Singleton
    @Provides
    fun provideEngine(
        @ApplicationContext
        context: Context
    ): Engine {
        return Engine(context)
    }

    @Singleton
    @Provides
    @Named("string1")
    fun provideString() = "Ini adalah String yang akan di inject"

    @Singleton
    @Provides
    @Named("string2")
    fun provideString2() = "Ini adalah String yang akan di inject 2"

}