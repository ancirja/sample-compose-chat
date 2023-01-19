package com.sample.chat.domain.util

import com.sample.chat.config.AppConfig
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.domain.ChatItem
import com.sample.chat.domain.DateHeader
import com.sample.chat.domain.Message
import com.sample.chat.utils.toDateString
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun List<MessageEntity>.toChatItems(): List<ChatItem> {
    val result = mutableListOf<ChatItem>()
    for (i in indices) {
        val currentItem = get(i)
        if (i == 0) {
            result.add(DateHeader(currentItem.timestamp.toDateString()))
            result.add(Message(currentItem.text, currentItem.senderId == AppConfig.MY_USER_ID))
        } else {
            result.add(Message(currentItem.text, currentItem.senderId == AppConfig.MY_USER_ID))

            if (i + 1 < size) {
                val nextItem = get(i + 1)
                val diff = TimeUnit.MILLISECONDS.toHours(currentItem.timestamp - nextItem.timestamp)
                if (diff > 0) {
                    result.add(DateHeader(currentItem.timestamp.toDateString()))
                }
            }
        }
    }

    return result
}

private val randomMessages = listOf(
    "Wowsa sounds fun",
    "Yeah for sure that works. What time do you think?",
    "Ok cool!",
    "Nothing much",
    "Actually just about to go to shopping, got any recommendations for a good shoe shop? I'm fashion disaster",
    "The last one went hours",
)

fun randomMessage(): String {
    val index = Random.nextInt(randomMessages.size)
    return randomMessages[index]
}
