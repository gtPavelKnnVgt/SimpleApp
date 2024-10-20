package dev.whysoezzy.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Progress(modifier: Modifier = Modifier) {
    var progressValue by remember { mutableFloatStateOf(0f) }
    val progress = animateFloatAsState(
        targetValue = progressValue, animationSpec = tween(
            durationMillis = 10_000,
            easing = LinearEasing
        ),
        label = "Progress animation"
    )
    LaunchedEffect(Unit) {
        progressValue = 1f
    }
    LinearProgressIndicator(
        progress = { progress.value },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}