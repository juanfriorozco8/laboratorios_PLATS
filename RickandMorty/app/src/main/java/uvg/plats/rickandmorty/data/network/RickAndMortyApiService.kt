package uvg.plats.rickandmorty.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import uvg.plats.rickandmorty.data.network.dto.CharacterResponseDto
import uvg.plats.rickandmorty.data.network.dto.LocationResponseDto

interface RickAndMortyApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponseDto

    @GET("location")
    suspend fun getLocations(): LocationResponseDto

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        fun create(): RickAndMortyApiService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyApiService::class.java)
        }
    }
}