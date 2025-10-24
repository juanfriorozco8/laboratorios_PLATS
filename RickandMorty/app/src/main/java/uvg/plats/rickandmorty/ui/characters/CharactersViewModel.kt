package uvg.plats.rickandmorty.ui.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.plats.rickandmorty.data.local.RickAndMortyDatabase
import uvg.plats.rickandmorty.data.model.Character
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.repository.CharacterRepository

data class CharactersUiState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CharacterRepository(
        RickAndMortyDatabase.getDatabase(application).characterDao(),
        RickAndMortyApiService.create()
    )

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, hasError = false) }

            try {
                val characters = repository.getAllCharacters()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = characters,
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