package uvg.plats.rickandmorty.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.plats.rickandmorty.data.local.RickAndMortyDatabase
import uvg.plats.rickandmorty.data.model.Location
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.repository.LocationRepository
import uvg.plats.rickandmorty.navigation.LocationDetailDestination

data class LocationDetailUiState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)

class LocationDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val locationDetailDestination = savedStateHandle.toRoute<LocationDetailDestination>()
    private val locationId = locationDetailDestination.locationId

    private val repository = LocationRepository(
        RickAndMortyDatabase.getDatabase(application).locationDao(),
        RickAndMortyApiService.create()
    )

    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getLocation()
    }

    fun getLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            delay(2000)

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                try {
                    val location = repository.getLocationById(locationId)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = location,
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
            } else {
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