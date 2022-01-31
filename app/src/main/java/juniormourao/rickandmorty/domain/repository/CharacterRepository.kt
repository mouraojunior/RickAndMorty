package juniormourao.rickandmorty.domain.repository

import androidx.paging.PagingData
import juniormourao.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharactersByName(characterName: String): Flow<PagingData<Character>>
}