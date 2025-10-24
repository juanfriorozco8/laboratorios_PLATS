package uvg.plats.rickandmorty.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.plats.rickandmorty.data.local.RickAndMortyDatabase
import uvg.plats.rickandmorty.data.model.Location
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.repository.LocationRepository

data class LocationsUiState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LocationRepository(
        RickAndMortyDatabase.getDatabase(application).locationDao(),
        RickAndMortyApiService.create()
    )

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getLocations()
    }

    fun getLocations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            try {
                val locations = repository.getAllLocations()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = locations,
                        hasError = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }
}