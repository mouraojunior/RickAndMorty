package juniormourao.rickandmorty.presentation.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.domain.use_case.GetCharacters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacters
) : ViewModel() {
    private lateinit var _charactersFlow: Flow<PagingData<Character>>
    val charactersFlow: Flow<PagingData<Character>>
        get() = _charactersFlow

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        _charactersFlow = getCharactersUseCase()
            .cachedIn(viewModelScope)
    }
}