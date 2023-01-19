package com.sample.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sample.chat.presentation.theme.Typography

@Composable
fun ChatDateHeader(date: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = date,
            color = Color.LightGray,
            style = Typography.h2
        )
    }
}

@Preview
@Composable
fun DateHeaderPreview() {
    ChatDateHeader(date = "Thursday 11:59")
}
