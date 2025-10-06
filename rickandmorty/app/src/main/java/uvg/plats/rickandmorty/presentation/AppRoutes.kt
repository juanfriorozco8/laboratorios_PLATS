package uvg.plats.rickandmorty.presentation
import kotlinx.serialization.Serializable

sealed interface AppRoutes {
    @Serializable object Login : AppRoutes
    @Serializable object Main : AppRoutes
    @Serializable object Characters : AppRoutes
    @Serializable data class CharacterProfile(val id: Int) : AppRoutes
    @Serializable object Locations : AppRoutes
    @Serializable data class LocationDetail(val id: Int) : AppRoutes
    @Serializable object Profile : AppRoutes
}
