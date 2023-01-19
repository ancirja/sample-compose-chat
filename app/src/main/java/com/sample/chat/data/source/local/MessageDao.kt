package com.sample.chat.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.chat.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    fun insertAll(vararg message: MessageEntity)

    @Query("SELECT * FROM messages where sender_id = :senderId AND receiver_id = :receiverId")
    fun getAll(senderId: String, receiverId: String): Flow<List<MessageEntity>>
}
