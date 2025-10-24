package uvg.plats.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationResponseDto(
    @SerializedName("results")
    val results: List<LocationDto>
)