package com.sample.chat.domain

import androidx.lifecycle.ViewModel
import com.sample.chat.config.AppConfig
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.data.entities.UserEntity
import com.sample.chat.data.source.MessageDataSource
import com.sample.chat.data.source.UserDataSource
import com.sample.chat.domain.util.randomMessage
import com.sample.chat.domain.util.toChatItems
import com.sample.chat.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val userRepository: UserDataSource,
    private val messageRepository: MessageDataSource,
    private val coroutineContextProvider: CoroutineContextProvider,
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = coroutineContextProvider.IO

    init {
        insertTestUserToDataSource()
    }

    private fun insertTestUserToDataSource() {
        launch {
            userRepository.addUser(
                UserEntity(
                    id = AppConfig.OTHER_USER_ID,
                    name = "Sarah",
                    pictureUrl = "https://i.pinimg.com/564x/78/73/70/787370e61c34dfbfb798ce08ac75a610.jpg",
                ),
            ).collect()
        }
    }

    val user = userRepository.getUserById(AppConfig.OTHER_USER_ID)
        .map { user ->
            user?.let {
                User(it.id, it.name, it.pictureUrl)
            }
        }
    val chatItems = messageRepository.getAll(AppConfig.MY_USER_ID, AppConfig.OTHER_USER_ID)
        .map { it.toChatItems() }

    fun sendMessage(text: String) {
        launch {
            if (text.isNotBlank()) {
                messageRepository.add(
                    MessageEntity(AppConfig.MY_USER_ID, AppConfig.OTHER_USER_ID, text, Date().time),
                ).collect {
                    simulateMessageReceived()
                }
            }
        }
    }

    private fun simulateMessageReceived() {
        launch {
            flow {
                delay(3000)
                emit(randomMessage())
            }.map {
                MessageEntity(AppConfig.OTHER_USER_ID, AppConfig.MY_USER_ID, it, Date().time)
            }.onEach {
                messageRepository.add(it)
            }.collect()
        }
    }
}
