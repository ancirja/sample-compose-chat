package com.sample.chat.data.source

import com.sample.chat.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    fun getAll(senderId: String, receiverId: String): Flow<List<MessageEntity>>

    fun add(message: MessageEntity): Flow<Unit>
}
