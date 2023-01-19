package com.sample.chat.di

import android.content.Context
import androidx.room.Room
import com.sample.chat.config.AppConfig
import com.sample.chat.data.source.local.AppDatabase
import com.sample.chat.data.source.local.MessageDao
import com.sample.chat.data.source.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppConfig.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideMessageDao(database: AppDatabase): MessageDao = database.messageDao()
}
