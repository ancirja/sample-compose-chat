package com.sample.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.chat.domain.ChatItem
import com.sample.chat.domain.DateHeader
import com.sample.chat.domain.Message

@Composable
fun ChatRoom(
    modifier: Modifier = Modifier,
    items: List<ChatItem>,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(items.size) {
        listState.animateScrollToItem(items.size)
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(items) { item ->
            when (item) {
                is Message -> ChatMessage(message = item)
                is DateHeader -> ChatDateHeader(date = item.date)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatRoomPreview() {
    val items = listOf(
        DateHeader("Thursday 11:59"),
        Message("Text message 1", false),
        Message("Text message 1", true),
        DateHeader("Thursday 10:59"),
        Message("Text message 1", true),
        Message("Text message 1", false),
    )
    ChatRoom(items = items)
}
