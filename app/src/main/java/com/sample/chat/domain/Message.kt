package com.sample.chat.domain

data class Message(
    val text: String,
    val isMine: Boolean,
    val hasTail: Boolean,
) : ChatItem
