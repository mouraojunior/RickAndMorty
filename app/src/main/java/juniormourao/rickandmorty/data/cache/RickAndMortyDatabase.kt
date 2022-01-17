package juniormourao.rickandmorty.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import juniormourao.rickandmorty.data.cache.dao.CharacterDao
import juniormourao.rickandmorty.data.cache.dao.RemoteKeyDao
import juniormourao.rickandmorty.data.cache.entity.CharacterEntity
import juniormourao.rickandmorty.data.cache.entity.RemoteKeyEntity

@Database(entities = [CharacterEntity::class, RemoteKeyEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    abstract val remoteKeyDao: RemoteKeyDao
}