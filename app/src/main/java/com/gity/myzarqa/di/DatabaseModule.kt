package com.gity.myzarqa.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideDatabase(
//        @ApplicationContext context: Context
//    ): AppDatabase {
//        return databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "zarqa_db"
//        ).fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideProductDao(database: AppDatabase): ProductDao {
//        return database.productDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideCategoryDao(database: AppDatabase): CategoryDao {
//        return database.categoryDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTransactionDao(database: AppDatabase): TransactionDao {
//        return database.transactionDao()
//    }
}
