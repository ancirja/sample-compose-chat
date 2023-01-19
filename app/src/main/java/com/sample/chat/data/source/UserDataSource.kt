package com.sample.chat.data.source

import com.sample.chat.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun getUserById(userId: String): Flow<UserEntity>

    fun addUser(user: UserEntity): Flow<Unit>
}
