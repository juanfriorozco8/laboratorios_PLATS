package uvg.plats.rickandmorty.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uvg.plats.rickandmorty.data.datastore.UserPreferences
import uvg.plats.rickandmorty.data.repository.UserPreferencesRepository

data class LoginUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val isLoggedIn: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferencesRepository = UserPreferencesRepository(
        UserPreferences(application)
    )

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            userPreferencesRepository.userName.collect { name ->
                if (name != null) {
                    _uiState.update { it.copy(isLoggedIn = true) }
                }
            }
        }
    }

    fun onUserNameChange(name: String) {
        _uiState.update { it.copy(userName = name) }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            userPreferencesRepository.saveUserName(_uiState.value.userName)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = true
                )
            }
        }
    }
}