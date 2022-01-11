package juniormourao.rickandmorty.data.repository

import juniormourao.rickandmorty.core.Resource
import juniormourao.rickandmorty.data.cache.dao.CharacterDao
import juniormourao.rickandmorty.data.remote.RickAndMortyApi
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())
        try {
            val charactersApi = api.getCharacters().results
            if (charactersApi.isNotEmpty()) {
                characterDao.insertCharacters(charactersApi.map { it.toCharacter() })
            }
            emit(Resource.Success(characterDao.getCharacters().map { it.toCharacter() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error has occurred."))
        }
    }
}