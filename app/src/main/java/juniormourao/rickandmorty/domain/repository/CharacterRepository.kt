package juniormourao.rickandmorty.domain.repository

import juniormourao.rickandmorty.core.Resource
import juniormourao.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<Resource<List<Character>>>
}