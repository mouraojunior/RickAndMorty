package juniormourao.rickandmorty.presentation.character_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import juniormourao.rickandmorty.R
import juniormourao.rickandmorty.core.Extensions.safeNavigate
import juniormourao.rickandmorty.databinding.FragmentCharacterListBinding
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.presentation.adapters.CharacterListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list),
    CharacterListAdapter.CharacterClickListener {
    private lateinit var characterListBinding: FragmentCharacterListBinding
    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterListAdapter = CharacterListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListBinding = FragmentCharacterListBinding.bind(view)

        setupCharacterRecyclerView()
        setUpSwipeToRefresh()
        collectFromViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    private fun setUpSwipeToRefresh() {
        characterListBinding.swpRefreshCharacters.apply {
            setOnRefreshListener {
                characterListViewModel.onEvent(CharacterListEvent.GetAllCharactersByName(characterListViewModel.searchQuery.value))
                characterListBinding.swpRefreshCharacters.isRefreshing = false
                characterListBinding.rvCharacters.scrollToPosition(0)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_character, menu)
        val item = menu.findItem(R.id.searchCharacterMenu)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Type a character name"
        searchView.setQuery(characterListViewModel.searchQuery.value, true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                performSearchEvent(query)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun performSearchEvent(characterName: String) {
        characterListViewModel.onEvent(CharacterListEvent.GetAllCharactersByName(characterName))
    }

    private fun setupCharacterRecyclerView() {
        characterListAdapter.characterClickListener = this@CharacterListFragment
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
            characterListViewModel.charactersFlow
                .collectLatest {
                    characterListAdapter.submitData(it)
                }
        }
    }

    override fun onCharacterClicked(character: Character?) {
        character?.let {
            findNavController().safeNavigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    it
                )
            )
        }
    }
}