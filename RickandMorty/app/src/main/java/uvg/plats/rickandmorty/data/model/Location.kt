package uvg.plats.rickandmorty.data.model

import kotlinx.serialization.Serializable
@Serializable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)