package uvg.plats.rickandmorty.presentation.loginFeature

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uvg.plats.rickandmorty.presentation.AppRoutes

fun NavGraphBuilder.loginRoute(
    onLoginClick: () -> Unit
) {
    composable<AppRoutes.Login> {
        LoginScreen(onStart = onLoginClick)
    }
}
