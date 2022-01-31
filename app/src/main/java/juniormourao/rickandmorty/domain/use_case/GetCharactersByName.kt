package juniormourao.rickandmorty.domain.use_case

import androidx.paging.PagingData
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersByName(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(characterName: String): Flow<PagingData<Character>> =
        characterRepository.getCharactersByName(characterName)
}