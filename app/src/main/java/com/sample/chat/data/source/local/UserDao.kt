package com.sample.chat.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.chat.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insertAll(vararg users: UserEntity)

    @Query("SELECT * from users where id = :userId limit 1")
    fun getUserById(userId: String): Flow<UserEntity>
}
