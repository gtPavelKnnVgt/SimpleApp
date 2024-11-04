package dev.whysoezzy.ui.details

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.ui.R
import dev.whysoezzy.ui.details.vm.DetailsState
import dev.whysoezzy.ui.details.vm.DetailsViewModel
import dev.whysoezzy.ui.views.Like
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, vm: DetailsViewModel = koinViewModel()) {
    val state = vm.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(
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
                },
                actions = {
                    if (state.value is DetailsState.Content) {
                        val content = state.value as DetailsState.Content
                        val like = remember { mutableStateOf(content.element.like) }
                        Like(modifier = Modifier.padding(end = 8.dp), like = like)
                        LaunchedEffect(like.value) {
                            vm.like(content.element, like.value)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (val st = state.value) {
                is DetailsState.Content -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column {
                            AsyncImage(
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                model = st.element.image,
                                contentDescription = null
                            )
                            Text(text = st.element.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = st.element.date, style = MaterialTheme.typography.bodyMedium)
                            Text(text = st.element.country, style = MaterialTheme.typography.bodyMedium)
                            Text(text = st.read.toString(), style = MaterialTheme.typography.bodyMedium)
                            Progress(vm = vm)
                        }

                    }
                }

                is DetailsState.Error -> {
                    Text(
                        text = st.message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                DetailsState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LaunchedEffect(Unit) {
            vm.markAsRead()
        }

        Column {
            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = element.image,
                contentDescription = null
            )
            Text(text = element.title, style = MaterialTheme.typography.titleLarge)
            Text(text = element.date, style = MaterialTheme.typography.bodyMedium)
            Text(text = element.country, style = MaterialTheme.typography.bodyMedium)
            Progress(vm = vm)
        }

    }
}


@Composable
fun Progress(vm: DetailsViewModel) {
    var progressValue by remember { mutableStateOf(0f) }
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
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        progress = { progress.value }
    )
    LaunchedEffect(Unit) {
        progressValue = 1f
    }
}