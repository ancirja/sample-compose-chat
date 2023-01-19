package com.sample.chat.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @ColumnInfo(name = "sender") val sender: String,
    @ColumnInfo(name = "receiver") val receiver: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "timestamp") @PrimaryKey val timestamp: Long,
)
