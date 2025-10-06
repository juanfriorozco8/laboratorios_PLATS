package uvg.plats.rickandmorty.presentation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uvg.plats.rickandmorty.presentation.AppRoutes

fun NavGraphBuilder.profileRoute(
    onLogout: () -> Unit
) {
    composable<AppRoutes.Profile> {
        ProfileScreen(
            onLogout = onLogout
        )
    }
}
