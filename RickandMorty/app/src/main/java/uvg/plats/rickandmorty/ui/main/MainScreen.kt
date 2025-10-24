package uvg.plats.rickandmorty.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import uvg.plats.rickandmorty.R
import uvg.plats.rickandmorty.navigation.CharacterDetailDestination
import uvg.plats.rickandmorty.navigation.CharactersDestination
import uvg.plats.rickandmorty.navigation.CharactersNavGraph
import uvg.plats.rickandmorty.navigation.LocationDetailDestination
import uvg.plats.rickandmorty.navigation.LocationsDestination
import uvg.plats.rickandmorty.navigation.LocationsNavGraph
import uvg.plats.rickandmorty.navigation.ProfileDestination
import uvg.plats.rickandmorty.ui.characters.CharacterDetailScreen
import uvg.plats.rickandmorty.ui.characters.CharactersScreen
import uvg.plats.rickandmorty.ui.locations.LocationDetailScreen
import uvg.plats.rickandmorty.ui.locations.LocationsScreen
import uvg.plats.rickandmorty.ui.profile.ProfileScreen
import androidx.compose.foundation.layout.padding

@Composable
fun MainScreen(
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(CharactersNavGraph::class)
                    } == true,
                    onClick = {
                        navController.navigate(CharactersNavGraph) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_characters),
                            contentDescription = "Characters"
                        )
                    },
                    label = { Text("Characters") }
                )

                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(LocationsNavGraph::class)
                    } == true,
                    onClick = {
                        navController.navigate(LocationsNavGraph) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentDestination?.hierarchy?.any {
                                    it.hasRoute(LocationsNavGraph::class)
                                } == true) Icons.Filled.Place else Icons.Outlined.Place,
                            contentDescription = "Locations"
                        )
                    },
                    label = { Text("Locations") }
                )

                NavigationBarItem(
                    selected = currentDestination?.hasRoute(ProfileDestination::class) == true,
                    onClick = {
                        navController.navigate(ProfileDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentDestination?.hasRoute(ProfileDestination::class) == true)
                                Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = CharactersNavGraph,
            modifier = Modifier.padding(paddingValues)
        ) {
            charactersGraph(navController = navController)
            locationsGraph(navController = navController)

            composable<ProfileDestination> {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}

fun NavGraphBuilder.charactersGraph(
    navController: NavHostController
) {
    navigation<CharactersNavGraph>(
        startDestination = CharactersDestination
    ) {
        composable<CharactersDestination> {
            CharactersScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(CharacterDetailDestination(characterId))
                }
            )
        }

        composable<CharacterDetailDestination> {
            CharacterDetailScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavGraphBuilder.locationsGraph(
    navController: NavHostController
) {
    navigation<LocationsNavGraph>(
        startDestination = LocationsDestination
    ) {
        composable<LocationsDestination> {
            LocationsScreen(
                onLocationClick = { locationId ->
                    navController.navigate(LocationDetailDestination(locationId))
                }
            )
        }

        composable<LocationDetailDestination> {
            LocationDetailScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}