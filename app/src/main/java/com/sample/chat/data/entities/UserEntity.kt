package com.sample.chat.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "picture_url")
    @PrimaryKey
    val pictureUrl: String,
)
