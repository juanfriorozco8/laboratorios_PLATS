package uvg.plats.rickandmorty.data.repository

import uvg.plats.rickandmorty.data.local.dao.LocationDao
import uvg.plats.rickandmorty.data.local.entity.toEntity
import uvg.plats.rickandmorty.data.local.entity.toLocation
import uvg.plats.rickandmorty.data.model.Location
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.network.util.NetworkError

class LocationRepository(
    private val locationDao: LocationDao,
    private val apiService: RickAndMortyApiService
) {

    suspend fun getAllLocations(): List<Location> {
        val localLocations = locationDao.getAllLocations()

        if (localLocations.isNotEmpty()) {
            return localLocations.map { it.toLocation() }
        }

        try {
            val response = apiService.getLocations()
            val locations = response.results.map { it.toEntity() }
            locationDao.insertAll(locations)
            return locations.map { it.toLocation() }
        } catch (e: Exception) {
            throw NetworkError.Unknown(e)
        }
    }

    suspend fun getLocationById(id: Int): Location {
        return locationDao.getLocationById(id).toLocation()
    }
}