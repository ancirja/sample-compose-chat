package com.sample.chat

import com.google.common.truth.Truth.assertThat
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.domain.util.toChatItems
import java.util.Calendar
import java.util.Date
import org.junit.Test

class ChatItemsTests {
    @Test
    fun `empty messages`() {
        val messages = listOf<MessageEntity>()
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(1)
    }

    @Test
    fun `one message`() {
        val messages = listOf(MessageEntity("any", "any", "any", Date().time))
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(2)
    }

    @Test
    fun `one message from right now, one from two hours ago`() {
        val calendar = Calendar.getInstance()
        val secondMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 1)
        val firstMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
    }

    @Test
    fun `all messages from the same hour`() {
        val calendar = Calendar.getInstance()
        val thirdMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.MINUTE) - 1)
        val secondMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.MINUTE) - 1)
        val firstMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage, thirdMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
    }
}
