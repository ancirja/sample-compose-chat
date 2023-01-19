package com.sample.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.sample.chat.R
import com.sample.chat.domain.User
import com.sample.chat.presentation.theme.RadicalRed
import com.sample.chat.presentation.theme.Typography

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    bottomElevation: Dp = 8.dp,
    user: User?,
    onClickBackButton: () -> Unit,
    onClickMoreButton: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            val (content, moreButton) = createRefs()

            Row(
                modifier = Modifier
                    .constrainAs(content) {
                        width = Dimension.fillToConstraints
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(moreButton.start)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onClickBackButton) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = RadicalRed,
                        contentDescription = stringResource(id = R.string.back),
                    )
                }

                if (user != null) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, RadicalRed, CircleShape),
                        model = user.profileUrl,
                        contentDescription = stringResource(id = R.string.avatar),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = user.name,
                        style = Typography.h1,
                        color = Color.Black,
                    )
                }
            }

            IconButton(
                modifier = Modifier.constrainAs(moreButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                onClick = onClickMoreButton,
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    tint = Color.Gray,
                    contentDescription = stringResource(id = R.string.more),
                )
            }
        }

        if (bottomElevation.value > 0) {
            val colorStops = arrayOf(
                0.0f to Color.LightGray,
                1f to Color.Transparent,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomElevation)
                    .background(brush = Brush.verticalGradient(colorStops = colorStops)),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopAppBarPreview() {
    val user = User(
        id = "id",
        name = "Sarah",
        profileUrl = "https://i.pinimg.com/564x/78/73/70/787370e61c34dfbfb798ce08ac75a610.jpg",
    )
    TopAppBar(
        user = user,
        onClickBackButton = {},
        onClickMoreButton = {},
    )
}
