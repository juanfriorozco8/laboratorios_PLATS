package uvg.plats.rickandmorty.data.repository

import uvg.plats.rickandmorty.data.local.dao.CharacterDao
import uvg.plats.rickandmorty.data.local.entity.toCharacter
import uvg.plats.rickandmorty.data.local.entity.toEntity
import uvg.plats.rickandmorty.data.model.Character
import uvg.plats.rickandmorty.data.network.RickAndMortyApiService
import uvg.plats.rickandmorty.data.network.util.NetworkError

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val apiService: RickAndMortyApiService
) {

    suspend fun getAllCharacters(): List<Character> {
        val localCharacters = characterDao.getAllCharacters()

        if (localCharacters.isNotEmpty()) {
            return localCharacters.map { it.toCharacter() }
        }

        try {
            val response = apiService.getCharacters()
            val characters = response.results.map { it.toEntity() }
            characterDao.insertAll(characters)
            return characters.map { it.toCharacter() }
        } catch (e: Exception) {
            throw NetworkError.Unknown(e)
        }
    }

    suspend fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id).toCharacter()
    }
}