package com.sample.chat.data.source

import com.sample.chat.data.entities.UserEntity
import com.sample.chat.data.source.local.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val localDataSource: UserDao,
) : UserDataSource {
    override fun getUserById(userId: String): Flow<UserEntity> =
        localDataSource.getUserById(userId)

    override fun addUser(user: UserEntity): Flow<Unit> =
        flowOf(localDataSource.insertAll(user))
}
