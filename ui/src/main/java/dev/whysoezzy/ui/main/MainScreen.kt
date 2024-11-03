package dev.whysoezzy.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.ui.R
import dev.whysoezzy.ui.details.DetailsScreenRoute
import dev.whysoezzy.ui.main.vm.MainState
import dev.whysoezzy.ui.main.vm.MainViewModel
import dev.whysoezzy.ui.views.Like
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 32.dp)
                )
            },
            actions = {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 18.dp)
                )
            })
        Box(modifier = Modifier.fillMaxWidth()) {
            when (val st = state) {
                is MainState.Content -> {
                    ContentState(
                        navController = navController,
                        list = st.list
                    ) { element, like ->
                        viewModel.like(element, like)
                    }
                }

                is MainState.Error -> {
                    Text(text = "Error")
                }

                MainState.Loading -> LoadingState()
            }
        }
    }

}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ContentState(
    navController: NavController,
    list: List<ListElementEntity>,
    onLike: (ListElementEntity, Boolean) -> Unit
) {
    LazyColumn {
        item {
            list.forEach { element ->
                ElementRow(navController, element, onLike)
            }
        }
    }
}

@Composable
private fun ElementRow(
    navController: NavController,
    element: ListElementEntity,
    onLike: (ListElementEntity, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { navController.navigate(DetailsScreenRoute(element.id)) }
    ) {
        AsyncImage(
            model = element.image,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(shape = RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
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
        }
        val like = remember { mutableStateOf(element.like) }
        Like(like = like, modifier = Modifier.align(Alignment.CenterVertically))
        LaunchedEffect(like.value) {
            onLike.invoke(element, like.value)
        }

    }
}

@Preview
@Composable
fun MainScreenPreview() {
//    MainScreen()
}
