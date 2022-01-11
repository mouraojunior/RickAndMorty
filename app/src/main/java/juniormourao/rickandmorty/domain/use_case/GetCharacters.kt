package juniormourao.rickandmorty.domain.use_case

import juniormourao.rickandmorty.domain.repository.CharacterRepository

class GetCharacters(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke() = characterRepository.getCharacters()
}