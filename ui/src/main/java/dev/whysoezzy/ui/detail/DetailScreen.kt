package dev.whysoezzy.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    id: String = "",
    navController: NavController
){
    Column {
        Text(text = "Hello world")
    }
}