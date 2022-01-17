package juniormourao.rickandmorty.domain.repository

import androidx.paging.PagingData
import juniormourao.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}