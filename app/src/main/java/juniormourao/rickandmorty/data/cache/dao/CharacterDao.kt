package juniormourao.rickandmorty.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import juniormourao.rickandmorty.data.cache.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("Select * from tbCharacter")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT COUNT(id) from tbCharacter")
    suspend fun countCharacters(): Int
}