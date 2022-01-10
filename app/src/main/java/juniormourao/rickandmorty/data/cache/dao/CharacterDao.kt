package juniormourao.rickandmorty.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniormourao.rickandmorty.data.cache.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("Select * from tbCharacter")
    suspend fun getCharacters(): List<CharacterEntity>
}