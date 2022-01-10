package juniormourao.rickandmorty.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import juniormourao.rickandmorty.data.cache.dao.CharacterDao
import juniormourao.rickandmorty.data.cache.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}