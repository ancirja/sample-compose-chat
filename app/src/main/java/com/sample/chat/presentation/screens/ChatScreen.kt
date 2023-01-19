package com.sample.chat.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.chat.R
import com.sample.chat.domain.ChatViewModel
import com.sample.chat.presentation.components.ChatInput
import com.sample.chat.presentation.components.ChatRoom
import com.sample.chat.presentation.components.TopAppBar

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    onClickBackButton: () -> Unit,
) {
    val user = viewModel.user.collectAsState(initial = null)
    val chatItems = viewModel.chatItems.collectAsState(initial = emptyList())
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                user = user.value,
                onClickBackButton = onClickBackButton,
                onClickMoreButton = {},
            )
        },
        content = {
            ChatRoom(modifier = Modifier.padding(it), items = chatItems.value)
        },
        bottomBar = {
            ChatInput(
                value = "",
                placeholder = stringResource(id = R.string.input_field_placeholder),
                onClickSend = {
                    viewModel.sendMessage(it)
                },
            )
        },
    )
}

@Composable
@Preview
fun ChatScreenPreview() {
    ChatScreen(onClickBackButton = {})
}
