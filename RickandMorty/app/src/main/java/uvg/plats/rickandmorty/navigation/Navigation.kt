package uvg.plats.rickandmorty.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

@Serializable
object MainDestination

@Serializable
object CharactersNavGraph

@Serializable
object CharactersDestination

@Serializable
data class CharacterDetailDestination(val characterId: Int)

@Serializable
object LocationsNavGraph

@Serializable
object LocationsDestination

@Serializable
data class LocationDetailDestination(val locationId: Int)

@Serializable
object ProfileDestination