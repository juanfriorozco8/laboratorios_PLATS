package uvg.plats.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uvg.plats.rickandmorty.navigation.LoginDestination
import uvg.plats.rickandmorty.navigation.MainDestination
import uvg.plats.rickandmorty.ui.login.LoginScreen
import uvg.plats.rickandmorty.ui.main.MainScreen
import uvg.plats.rickandmorty.ui.theme.RickandMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickandMortyTheme {
                Surface {
                    RickAndMortyApp()
                }
            }
        }
    }
}

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<LoginDestination> {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(MainDestination) {
                        popUpTo(LoginDestination) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<MainDestination> {
            val mainNavController = rememberNavController()
            MainScreen(
                navController = mainNavController,
                onLogout = {
                    navController.navigate(LoginDestination) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
