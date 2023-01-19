package com.sample.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.chat.domain.Message
import com.sample.chat.presentation.messageRoundCorner
import com.sample.chat.presentation.theme.RadicalRed
import com.sample.chat.presentation.theme.Typography
import com.sample.chat.presentation.theme.WhiteLilac
import com.sample.chat.presentation.zeroRoundCorner

@Composable
fun ChatMessage(message: Message) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val alignment = if (message.isMine) Alignment.CenterEnd else Alignment.CenterStart
        val backgroundColor = if (message.isMine) RadicalRed else WhiteLilac
        val textColor = if (message.isMine) Color.White else Color.DarkGray
        val bottomEndCornerSize = if (message.isMine) zeroRoundCorner else messageRoundCorner
        val bottomStartCornerSize = if (message.isMine) messageRoundCorner else zeroRoundCorner

        Box(
            modifier = Modifier
                .align(alignment)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(
                        topStart = messageRoundCorner,
                        topEnd = messageRoundCorner,
                        bottomStart = bottomStartCornerSize,
                        bottomEnd = bottomEndCornerSize,
                    ),
                ),
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message.text,
                color = textColor,
                style = Typography.body1,
            )
        }
    }
}

@Preview
@Composable
fun ChatMessagePreview() {
    ChatMessage(message = Message("Some text here", isMine = false))
}
