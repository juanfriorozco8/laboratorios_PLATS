package uvg.plats.rickandmorty.ui.characters

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
import uvg.plats.rickandmorty.data.model.Character
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.repository.CharacterRepository
import uvg.plats.rickandmorty.navigation.CharacterDetailDestination

data class CharacterDetailUiState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)

class CharacterDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val characterDetailDestination = savedStateHandle.toRoute<CharacterDetailDestination>()
    private val characterId = characterDetailDestination.characterId

    private val repository = CharacterRepository(
        RickAndMortyDatabase.getDatabase(application).characterDao(),
        RickAndMortyApiService.create()
    )

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            delay(2000)

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                try {
                    val character = repository.getCharacterById(characterId)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = character,
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
