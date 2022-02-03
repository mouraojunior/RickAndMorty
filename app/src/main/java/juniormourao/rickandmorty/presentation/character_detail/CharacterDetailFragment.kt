package juniormourao.rickandmorty.presentation.character_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import juniormourao.rickandmorty.R
import juniormourao.rickandmorty.databinding.FragmentCharacterDetailBinding
import juniormourao.rickandmorty.domain.model.Character

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {
    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var character: Character

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailBinding.bind(view)
        getArgs()
    }

    private fun getArgs() {
        character = args.character
        bindCharacterDetails()
    }

    private fun bindCharacterDetails() {
        setActionBarTitle()
        binding.apply {
            character.let {
                tvCharacterName.text = it.name
                tvCharacterSpecies.text = it.species
                tvCharacterGender.text = it.gender
                tvCharacterStatus.text = it.status
                tvCharacterLocation.text = it.location
                tvCharacterOrigin.text = it.origin
                imgCharacterImage.load(character.image)
            }
        }
    }

    private fun setActionBarTitle() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = character.name
    }
}