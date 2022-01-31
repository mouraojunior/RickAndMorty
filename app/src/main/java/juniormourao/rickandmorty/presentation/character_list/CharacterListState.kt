package juniormourao.rickandmorty.presentation.character_list

import androidx.paging.PagingData
import juniormourao.rickandmorty.domain.model.Character

sealed class CharacterListState {
    data class Success(val charactersPaged: PagingData<Character>) : CharacterListState()
    data class Error(val message: String) : CharacterListState()
    object Loading : CharacterListState()
}