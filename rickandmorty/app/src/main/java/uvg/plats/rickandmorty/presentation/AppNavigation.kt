package uvg.plats.rickandmorty.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uvg.plats.rickandmorty.presentation.loginFeature.loginRoute
import uvg.plats.rickandmorty.presentation.main.mainRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    onFinishApp: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Login,
        modifier = modifier
    ) {
        loginRoute(
            onLoginClick = {
                navController.navigate(AppRoutes.Main) {
                    popUpTo(AppRoutes.Login) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        mainRoute(
            onLogout = {
                navController.navigate(AppRoutes.Login) {
                    popUpTo(AppRoutes.Main) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onFinishApp = onFinishApp
        )
    }
}
