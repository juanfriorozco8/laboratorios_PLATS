package uvg.plats.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class CharacterResponseDto(
    @SerializedName("results")
    val results: List<CharacterDto>
)