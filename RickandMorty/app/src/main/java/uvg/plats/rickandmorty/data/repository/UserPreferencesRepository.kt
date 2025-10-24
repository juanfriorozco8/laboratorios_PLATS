package uvg.plats.rickandmorty.data.repository

import kotlinx.coroutines.flow.Flow
import uvg.plats.rickandmorty.data.datastore.UserPreferences

class UserPreferencesRepository(
    private val userPreferences: UserPreferences
) {

    val userName: Flow<String?> = userPreferences.userName

    suspend fun saveUserName(name: String) {
        userPreferences.saveUserName(name)
    }

    suspend fun clearUserName() {
        userPreferences.clearUserName()
    }
}