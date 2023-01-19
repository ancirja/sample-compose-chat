package com.sample.chat

import com.google.common.truth.Truth.assertThat
import com.sample.chat.config.AppConfig
import com.sample.chat.data.entities.MessageEntity
import com.sample.chat.domain.DateHeader
import com.sample.chat.domain.Message
import com.sample.chat.domain.util.toChatItems
import org.junit.Test
import java.util.Calendar
import java.util.Date

class ChatItemsTests {
    @Test
    fun `date headers for empty messages`() {
        val messages = listOf<MessageEntity>()
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(1)
        assertThat(chatItems[0]).isInstanceOf(DateHeader::class.java)
    }

    @Test
    fun `date headers for one message`() {
        val messages = listOf(MessageEntity("any", "any", "any", Date().time))
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(2)
        assertThat(chatItems[0]).isInstanceOf(DateHeader::class.java)
        assertThat(chatItems[1]).isInstanceOf(Message::class.java)
    }

    @Test
    fun `date headers for one message from right now, one from two hours ago`() {
        val calendar = Calendar.getInstance()
        val secondMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 1)
        val firstMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
        assertThat(chatItems[0]).isInstanceOf(DateHeader::class.java)
        assertThat(chatItems[1]).isInstanceOf(Message::class.java)
        assertThat(chatItems[2]).isInstanceOf(DateHeader::class.java)
        assertThat(chatItems[3]).isInstanceOf(Message::class.java)
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
        assertThat(chatItems[0]).isInstanceOf(DateHeader::class.java)
        assertThat(chatItems[1]).isInstanceOf(Message::class.java)
        assertThat(chatItems[2]).isInstanceOf(Message::class.java)
        assertThat(chatItems[3]).isInstanceOf(Message::class.java)
    }

    @Test
    fun `message tail for single message`() {
        val messages = listOf(MessageEntity("any", "any", "any", Date().time))
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(2)
        val message = chatItems[1] as Message
        assertThat(message.hasTail).isTrue()
    }

    @Test
    fun `message tail for one message from right now, one from two hours ago`() {
        val calendar = Calendar.getInstance()
        val secondMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 1)
        val firstMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
        var message = chatItems[1] as Message
        assertThat(message.hasTail).isTrue()
        message = chatItems[3] as Message
        assertThat(message.hasTail).isTrue()
    }

    @Test
    fun `message tail for all messages one after the other`() {
        val calendar = Calendar.getInstance()
        val thirdMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.SECOND) - 1)
        val secondMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        calendar.set(Calendar.HOUR, calendar.get(Calendar.SECOND) - 1)
        val firstMessage = MessageEntity("any", "any", "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage, thirdMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
        var message = chatItems[1] as Message
        assertThat(message.hasTail).isFalse()
        message = chatItems[2] as Message
        assertThat(message.hasTail).isFalse()
        message = chatItems[3] as Message
        assertThat(message.hasTail).isTrue()
    }

    @Test
    fun `message tail for all messages one after the other but from different users`() {
        val calendar = Calendar.getInstance()
        val thirdMessage = MessageEntity(AppConfig.OTHER_USER_ID, AppConfig.MY_USER_ID, "any", calendar.timeInMillis)

        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1)
        val secondMessage = MessageEntity(AppConfig.MY_USER_ID, AppConfig.OTHER_USER_ID, "any", calendar.timeInMillis)

        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1)
        val firstMessage = MessageEntity(AppConfig.OTHER_USER_ID, AppConfig.MY_USER_ID, "any", calendar.timeInMillis)

        val messages = listOf(firstMessage, secondMessage, thirdMessage)
        val chatItems = messages.toChatItems()

        assertThat(chatItems.size).isEqualTo(4)
        var message = chatItems[1] as Message
        assertThat(message.hasTail).isTrue()
        message = chatItems[2] as Message
        assertThat(message.hasTail).isTrue()
        message = chatItems[3] as Message
        assertThat(message.hasTail).isTrue()
    }
}
