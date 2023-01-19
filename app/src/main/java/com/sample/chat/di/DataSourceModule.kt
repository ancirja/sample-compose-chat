package com.sample.chat.di

import com.sample.chat.data.source.MessageDataSource
import com.sample.chat.data.source.MessageRepository
import com.sample.chat.data.source.UserDataSource
import com.sample.chat.data.source.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun provideUserRepository(repository: UserRepository): UserDataSource

    @Singleton
    @Binds
    abstract fun provideMessageRepository(repository: MessageRepository): MessageDataSource
}
