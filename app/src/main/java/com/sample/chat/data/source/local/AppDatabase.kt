package com.sample.chat.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.chat.config.AppConfig
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.data.entities.UserEntity

@Database(
    entities = [UserEntity::class, MessageEntity::class],
    version = AppConfig.DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun messageDao(): MessageDao
}
