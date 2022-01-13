package juniormourao.rickandmorty.presentation.character_list

import juniormourao.rickandmorty.domain.model.Character

sealed class CharacterListState {
    data class Success(val characterList: List<Character>) : CharacterListState()
    data class Error(val errorMessage: String) : CharacterListState()
    object Loading : CharacterListState()
}