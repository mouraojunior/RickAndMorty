package juniormourao.rickandmorty.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbCharacter")
data class CharacterEntity(
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val origin: String,
    val species: String,
    val status: String
)