package uvg.plats.rickandmorty.presentation.locationFeature

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import uvg.plats.rickandmorty.data.LocationDb
import uvg.plats.rickandmorty.presentation.AppRoutes
import uvg.plats.rickandmorty.presentation.locationFeature.list.LocationsScreen
import uvg.plats.rickandmorty.presentation.locationFeature.detail.LocationDetail

fun NavGraphBuilder.locationsGraph(
    onLocationClick: (Int) -> Unit,
    onNavigateTo: (Any) -> Unit,
    onPop: () -> Unit,
    onFinishApp: () -> Unit
) {
    navigation<AppRoutes.Locations>(startDestination = AppRoutes.Locations) {
        composable<AppRoutes.Locations> {
            BackHandler { onFinishApp() }
            val db = LocationDb()
            val locations = db.getAllLocations()
            LocationsScreen(
                locations = locations,
                onOpenDetail = { id -> onNavigateTo(AppRoutes.LocationDetail(id)) }
            )
        }
        composable<AppRoutes.LocationDetail> { backStack ->
            val args = backStack.toRoute<AppRoutes.LocationDetail>()
            val db = LocationDb()
            val location = db.getLocationById(args.id)
            LocationDetail(
                location = location,
                onBack = onPop
            )
        }
    }
}

