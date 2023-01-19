package com.sample.chat.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.chat.domain.ChatViewModel
import com.sample.chat.presentation.ChatRoom
import com.sample.chat.presentation.components.ChatInput
import com.sample.chat.presentation.components.TopAppBar

@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val user = viewModel.user.collectAsState(initial = null)
    val chatItems = viewModel.allMessages.collectAsState(initial = emptyList())
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                user = user.value,
                onClickBackButton = {
                },
                onClickMoreButton = {
                },
            )
        },
        content = {
            ChatRoom(modifier = Modifier.padding(it), items = chatItems.value)
        },
        bottomBar = {
            ChatInput(value = "", onClickSend = {
                viewModel.sendMessage(it)
            })
        },
    )
}

@Composable
@Preview
fun ChatScreenPreview() {
    ChatScreen()
}
