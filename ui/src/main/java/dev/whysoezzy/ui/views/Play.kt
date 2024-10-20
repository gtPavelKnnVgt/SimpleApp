package dev.whysoezzy.ui.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import dev.whysoezzy.ui.R
import kotlinx.coroutines.delay

@Composable
fun Play(
    modifier: Modifier = Modifier,
    isPlaying: MutableState<Boolean>
){
    val heartColor = animateColorAsState(
        targetValue = if (isPlaying.value) Color("#F84A00".toColorInt()) else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 200),
        label = "LikeColorAnimation"
    )


    val isFirstShown = remember { mutableStateOf(true) }
    val heartSize = remember { mutableStateOf(72.dp) }
    val heartSizeState = animateDpAsState(
        targetValue = heartSize.value,
        animationSpec = tween(durationMillis = 100),
        label = "HeartSizeAnimation"
    )

    LaunchedEffect(isPlaying.value) {

        if (isFirstShown.value) {
            isFirstShown.value = false
        } else {
            heartSize.value = 96.dp
            delay(100)
            heartSize.value = 72.dp
        }
    }

    Box(modifier = modifier
        .size(96.dp)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = false)
        ) { isPlaying.value = !isPlaying.value }
    ) {
        Icon(
            modifier = Modifier
                .size(heartSizeState.value)
                .align(Alignment.Center),
            painter = painterResource(id = if(isPlaying.value) R.drawable.play else R.drawable.pause),
            contentDescription = "like",
            tint = heartColor.value
        )
    }
}

@Preview
@Composable
fun PreviewPlay() {
    val isPlaying = remember { mutableStateOf(false) }
    Play(isPlaying = isPlaying)
}