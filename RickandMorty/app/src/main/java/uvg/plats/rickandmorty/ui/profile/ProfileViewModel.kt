package uvg.plats.rickandmorty.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.plats.rickandmorty.data.datastore.UserPreferences
import uvg.plats.rickandmorty.data.repository.UserPreferencesRepository

data class ProfileUiState(
    val userName: String = ""
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferencesRepository = UserPreferencesRepository(
        UserPreferences(application)
    )

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userPreferencesRepository.userName.collect { name ->
                _uiState.update { it.copy(userName = name ?: "") }
            }
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            userPreferencesRepository.clearUserName()
            onLogoutComplete()
        }
    }
}