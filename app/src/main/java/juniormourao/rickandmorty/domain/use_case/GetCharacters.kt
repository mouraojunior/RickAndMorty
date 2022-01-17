package juniormourao.rickandmorty.domain.use_case

import androidx.paging.PagingData
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacters(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<PagingData<Character>> = characterRepository.getCharacters()
}