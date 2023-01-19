package com.sample.chat.domain.util

import com.sample.chat.config.AppConfig
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.domain.ChatItem
import com.sample.chat.domain.DateHeader
import com.sample.chat.domain.Message
import com.sample.chat.utils.toDateString
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * This method will map items from Messages ([MessageEntity]) to [ChatItem]s
 * that can be text messages ([Message]) and date headers ([DateHeader]).
 *
 * If the message list is empty, then the result is a list containing only the current date header
 * If the one message is in the same hour like the previous message then the date header will be added before of them
 *
 * A message has a tail when any of the following 3 criteria are met:
 *      - It is the most recent message in the conversation
 *      - The message after it is sent by the other user
 *      - The message after it was sent more than 20 seconds afterwards
 *
 */
fun List<MessageEntity>.toChatItems(): List<ChatItem> {
    val result = mutableListOf<ChatItem>()

    if (isEmpty()) {
        result.add(DateHeader(Date().toDateString()))
    } else {
        for (i in indices) {
            val currentItem = get(i)
            if (i == 0) {
                result.add(DateHeader(currentItem.timestamp.toDateString()))
            } else {
                val previousItem = get(i - 1)

                val diffInHours = TimeUnit.MILLISECONDS.toHours(currentItem.timestamp - previousItem.timestamp)
                if (diffInHours >= 1) {
                    result.add(DateHeader(currentItem.timestamp.toDateString()))
                }
            }

            val nextItem = if (i + 1 in indices) {
                get(i + 1)
            } else {
                null
            }

            val hasTail = if (nextItem == null) {
                true
            } else {
                val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(nextItem.timestamp - currentItem.timestamp)
                diffInSeconds > AppConfig.MESSAGE_TAIL_DELAY || nextItem.receiverId != currentItem.receiverId
            }

            result.add(
                Message(
                    text = currentItem.text,
                    isMine = currentItem.senderId == AppConfig.MY_USER_ID,
                    hasTail = hasTail,
                ),
            )
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

/**
 * Generates random message for testing purpose
 */
fun randomMessage(): String {
    val index = Random.nextInt(randomMessages.size)
    return randomMessages[index]
}
