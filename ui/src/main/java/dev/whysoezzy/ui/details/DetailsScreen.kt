package dev.whysoezzy.ui.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.ui.R
import dev.whysoezzy.ui.details.vm.DetailsState
import dev.whysoezzy.ui.details.vm.DetailsViewModel
import dev.whysoezzy.ui.views.Like
import dev.whysoezzy.ui.views.Play
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, vm: DetailsViewModel = koinViewModel()) {
    val state = vm.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = ripple(bounded = false)
                        ) {
                            navController.navigateUp()
                        },
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = null
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            when (val st = state.value) {
                is DetailsState.Content -> {
                    ContentState(element = st.element, vm = vm)
                }

                is DetailsState.Error -> {
                    Text(text = st.message)
                }

                DetailsState.Loading -> {
                    CircularProgressIndicator()
                }
            }

        }

    }


}

@Composable
fun ContentState(
    element: ListElementEntity,
    vm: DetailsViewModel
) {
    val isPlaying = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = element.image,
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
                .clip(shape = RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = element.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "${element.date} — ${element.country}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
        Progress(
            vm = vm,
            isPlaying = isPlaying,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.align(Alignment.Center)) {
                Icon(
                    painter = painterResource(id = R.drawable.skip_previous),
                    contentDescription = "like",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(72.dp)
                        .clickable {
                            vm.like(element, true)
                        }
                        .padding(top = 16.dp)
                )
                Play(isPlaying = isPlaying)
                Icon(
                    painter = painterResource(id = R.drawable.skip_next),
                    contentDescription = "dislike",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(72.dp)
                        .clickable {
                            vm.like(element, false)
                        }
                        .padding(top = 16.dp)
                )
            }
            val like = remember { mutableStateOf(element.like) }
            Like(like = like, modifier = Modifier.align(Alignment.CenterEnd).padding(bottom = 8.dp))
        }

    }

}

@Composable
fun Progress(
    vm: DetailsViewModel,
    isPlaying: MutableState<Boolean> ,
    modifier: Modifier = Modifier
) {
    var progressValue by remember { mutableFloatStateOf(0f) }
    val progress = animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = 10_000,
            easing = LinearEasing
        ),
        finishedListener = {
            vm.markAsRead()
        }
    )
    LaunchedEffect(isPlaying.value) {
        if (isPlaying.value) {
            while (progressValue < 1f) {
                delay(100)
                progressValue += 0.01f
            }
        }
    }
    LinearProgressIndicator(
        progress = { progress.value },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}


