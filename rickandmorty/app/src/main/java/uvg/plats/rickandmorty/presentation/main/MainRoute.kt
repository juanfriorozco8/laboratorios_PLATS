@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package uvg.plats.rickandmorty.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import uvg.plats.rickandmorty.data.CharacterDb
import uvg.plats.rickandmorty.data.LocationDb
import uvg.plats.rickandmorty.presentation.AppRoutes
import uvg.plats.rickandmorty.presentation.characterFeature.list.CharactersScreen
import uvg.plats.rickandmorty.presentation.characterFeature.profile.CharacterProfile
import uvg.plats.rickandmorty.presentation.locationFeature.detail.LocationDetail
import uvg.plats.rickandmorty.presentation.locationFeature.list.LocationsScreen
import uvg.plats.rickandmorty.presentation.profile.ProfileScreen
import uvg.plats.rickandmorty.presentation.model.CharacterUI
import uvg.plats.rickandmorty.presentation.model.Location


fun NavGraphBuilder.mainRoute(
    onLogout: () -> Unit,
    onFinishApp: () -> Unit
) {
    composable<AppRoutes.Main> {
        val nav = rememberNavController()
        Scaffold(
            bottomBar = {
                val backStack by nav.currentBackStackEntryAsState()
                val current = backStack?.destination?.route
                NavigationBar {
                    NavigationBarItem(
                        selected = current == AppRoutes.Characters::class.qualifiedName,
                        onClick = { nav.navigate(AppRoutes.Characters) { launchSingleTop = true } },
                        label = { Text("Characters") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = current == AppRoutes.Locations::class.qualifiedName,
                        onClick = { nav.navigate(AppRoutes.Locations) { launchSingleTop = true } },
                        label = { Text("Locations") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = current == AppRoutes.Profile::class.qualifiedName,
                        onClick = { nav.navigate(AppRoutes.Profile) { launchSingleTop = true } },
                        label = { Text("Profile") },
                        icon = {}
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = nav,
                startDestination = AppRoutes.Characters,
                modifier = Modifier.padding(innerPadding)
            )
            {
                composable<AppRoutes.Characters> {
                    BackHandler { onFinishApp() }
                    val characters: List<CharacterUI> = CharacterDb().getAllCharacters()
                    CharactersScreen(
                        characters = characters,
                        onOpenDetail = { id -> nav.navigate(AppRoutes.CharacterProfile(id)) }
                    )
                }
                composable<AppRoutes.CharacterProfile> { entry ->
                    val args = entry.toRoute<AppRoutes.CharacterProfile>()
                    val character: CharacterUI = CharacterDb().getCharacterById(args.id)
                    CharacterProfile(
                        character = character,
                        onBack = { nav.popBackStack() }
                    )
                }
                composable<AppRoutes.Locations> {
                    BackHandler { onFinishApp() }
                    val locations: List<Location> = LocationDb().getAllLocations()
                    LocationsScreen(
                        locations = locations,
                        onOpenDetail = { id -> nav.navigate(AppRoutes.LocationDetail(id)) }
                    )
                }
                composable<AppRoutes.LocationDetail> { entry ->
                    val args = entry.toRoute<AppRoutes.LocationDetail>()
                    val location: Location = LocationDb().getLocationById(args.id)
                    LocationDetail(
                        location = location,
                        onBack = { nav.popBackStack() }
                    )
                }
                composable<AppRoutes.Profile> {
                    ProfileScreen(onLogout = onLogout)
                }
            }
        }
    }
}
