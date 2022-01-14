package juniormourao.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import juniormourao.rickandmorty.core.GlideApp
import juniormourao.rickandmorty.databinding.CharacterListItemBinding
import juniormourao.rickandmorty.domain.model.Character

class CharacterListAdapter :
    ListAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(CharacterComparator()) {

    class CharacterListViewHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCharacter(character: Character) {
            binding.apply {
                tvCharacterName.text = character.name
                tvCharacterId.text = character.id.toString()
                GlideApp.with(binding.imgvCharacterImage.context)
                    .load(character.image)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imgvCharacterImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindCharacter(it) }
    }

    class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}