package com.sample.chat.data.source

import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.data.source.local.MessageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val localDataSource: MessageDao,
) : MessageDataSource {
    override fun getAll(senderId: String, receiverId: String): Flow<List<MessageEntity>> =
        localDataSource.getAll(senderId, receiverId)

    override fun add(message: MessageEntity): Flow<Unit> =
        flowOf(localDataSource.insertAll(message))
}
