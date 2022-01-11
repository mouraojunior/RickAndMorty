package juniormourao.rickandmorty.presentation

import juniormourao.rickandmorty.domain.model.Character

sealed class CharacterListState {
    data class Success(private val characterList: List<Character>) : CharacterListState()
    data class Error(private val errorMessage: String) : CharacterListState()
    object Loading : CharacterListState()
}