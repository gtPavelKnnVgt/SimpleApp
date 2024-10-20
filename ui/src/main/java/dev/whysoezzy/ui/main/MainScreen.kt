package dev.whysoezzy.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.whysoezzy.domain.entity.ListElement
import dev.whysoezzy.ui.Progress
import dev.whysoezzy.ui.R
import dev.whysoezzy.ui.details.DetailsScreenRoute
import dev.whysoezzy.ui.main.vm.MainState
import dev.whysoezzy.ui.main.vm.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = koinViewModel()) {
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
                    ContentState(navController = navController, list = st.list)
                }

                is MainState.Error -> {
                    Text(text = "Error")
                }

                MainState.Loading -> Progress()
            }
        }
    }

}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun ContentState(navController: NavController, list: List<ListElement>) {
    LazyColumn {
        item {
            list.forEach { element ->
                ElementRow(navController, element)
            }
        }
    }
}

@Composable
private fun ElementRow(navController: NavController, element: ListElement) {
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
                .size(124.dp)
                .clip(shape = RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(text = element.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "${element.date} — ${element.country}", style = MaterialTheme.typography.titleMedium)
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                element.button?.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun MainScreenPreview() {
//    MainScreen()
}
