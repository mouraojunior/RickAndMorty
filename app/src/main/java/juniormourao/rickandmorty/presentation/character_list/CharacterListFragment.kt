package juniormourao.rickandmorty.presentation.character_list

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import juniormourao.rickandmorty.R
import juniormourao.rickandmorty.core.Extensions.showSnackBar
import juniormourao.rickandmorty.databinding.FragmentCharacterListBinding
import juniormourao.rickandmorty.presentation.adapters.CharacterListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list) {
    private lateinit var characterListBinding: FragmentCharacterListBinding
    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterListAdapter = CharacterListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListBinding = FragmentCharacterListBinding.bind(view)

        setupCharacterRecyclerView()
        collectFromViewModel()
    }

    private fun setupCharacterRecyclerView() {
        characterListBinding.apply {
            rvCharacters.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = characterListAdapter
            }
        }
    }

    private fun collectFromViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterListViewModel.characterListState.collectLatest { state ->
                    when (state) {
                        is CharacterListState.Error -> {
                            characterListBinding.apply {
                                pbLoading.visibility = GONE
                                root.showSnackBar(state.errorMessage)
                            }
                        }
                        is CharacterListState.Loading -> {
                            characterListBinding.apply {
                                pbLoading.visibility = VISIBLE
                            }
                        }
                        is CharacterListState.Success -> {
                            characterListBinding.pbLoading.visibility = GONE
                            characterListAdapter.submitList(state.characterList)
                        }
                    }
                }
            }
        }
    }
}