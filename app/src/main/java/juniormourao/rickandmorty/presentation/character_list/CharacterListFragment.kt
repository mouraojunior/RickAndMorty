package juniormourao.rickandmorty.presentation.character_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import juniormourao.rickandmorty.R
import juniormourao.rickandmorty.databinding.FragmentCharacterListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list) {
    private lateinit var characterListBinding: FragmentCharacterListBinding
    private val characterListViewModel: CharacterListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListBinding = FragmentCharacterListBinding.bind(view)

        collectFromViewModel()
    }

    private fun collectFromViewModel() {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    characterListViewModel.characterListState.collectLatest { state ->
                        when (state) {
                            is CharacterListState.Error -> println("ErrorState: ${state.errorMessage}")
                            is CharacterListState.Loading -> println("LoadingState")
                            is CharacterListState.Success -> println("SuccessState: ${state.characterList}")
                        }
                    }
                }
            }
    }
}