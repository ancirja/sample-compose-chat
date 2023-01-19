package com.sample.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sample.chat.presentation.theme.RadicalRed

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    value: String,
    topElevation: Dp = 8.dp,
    onClickSend: (String) -> Unit,
) {
    val sendButtonColor = remember { mutableStateOf(Color.Gray) }
    val currentText = remember { mutableStateOf(value) }

    Column {
        if (topElevation.value > 0) {
            val colorStops = arrayOf(
                0.0f to Color.Transparent,
                1f to Color.LightGray,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topElevation)
                    .background(brush = Brush.verticalGradient(colorStops = colorStops)),
            )
        }

        ConstraintLayout(
            modifier = modifier.fillMaxWidth(),
        ) {
            val (text, button) = createRefs()

            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(text) {
                        width = Dimension.fillToConstraints
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(button.start)
                    }
                    .onFocusChanged {
                        sendButtonColor.value = if (it.hasFocus) RadicalRed else Color.Gray
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = RadicalRed,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = RadicalRed,
                ),
                shape = RoundedCornerShape(16.dp),
                value = currentText.value,
                onValueChange = {
                    currentText.value = it
                },
            )

            IconButton(
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                onClick = {
                    onClickSend(currentText.value)
                    currentText.value = ""
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    tint = sendButtonColor.value,
                    contentDescription = "Send",
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatInputPreview() {
    ChatInput(value = "", onClickSend = {})
}
