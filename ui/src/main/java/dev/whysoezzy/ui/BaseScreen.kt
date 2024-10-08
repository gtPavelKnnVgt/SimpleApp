package dev.whysoezzy.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.whysoezzy.ui.navigation.NavGraph

@Composable
fun BaseScreen(){
    val navController = rememberNavController()
    Surface(modifier = Modifier.fillMaxSize()) {
        NavGraph(navController = navController)
    }
}