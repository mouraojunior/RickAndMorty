package juniormourao.rickandmorty.presentation.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import juniormourao.rickandmorty.core.Resource
import juniormourao.rickandmorty.domain.use_case.GetCharacters
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharacters
) : ViewModel() {
    private val _characterListState = MutableSharedFlow<CharacterListState>()
    var characterListState = _characterListState.asSharedFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(IO) {
            getCharactersUseCase().onEach { charactersResource ->
                withContext(Main) {
                    when (charactersResource) {
                        is Resource.Error -> {
                            _characterListState.emit(
                                CharacterListState.Error(
                                    charactersResource.message ?: "An unknown error has occurred."
                                )
                            )
                        }
                        is Resource.Loading -> _characterListState.emit(CharacterListState.Loading)
                        is Resource.Success -> {
                            _characterListState.emit(
                                CharacterListState.Success(
                                    charactersResource.data ?: emptyList()
                                )
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}