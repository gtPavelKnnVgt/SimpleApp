package org.pavelknnv.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.pavelknnv.ui.details.DetailsScreen
import org.pavelknnv.ui.details.DetailsScreenRoute
import org.pavelknnv.ui.main.MainScreen
import org.pavelknnv.ui.main.MainScreenRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = MainScreenRoute) {
                        composable<MainScreenRoute> {
                            MainScreen(navController = navController)
                        }
                        composable<DetailsScreenRoute> {
                            DetailsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
