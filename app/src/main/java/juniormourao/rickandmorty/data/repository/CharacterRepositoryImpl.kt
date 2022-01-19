package juniormourao.rickandmorty.data.repository

import androidx.paging.*
import juniormourao.rickandmorty.data.cache.RickAndMortyDatabase
import juniormourao.rickandmorty.data.paging.CharacterRemoteMediator
import juniormourao.rickandmorty.data.remote.RickAndMortyApi
import juniormourao.rickandmorty.domain.model.Character
import juniormourao.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val db: RickAndMortyDatabase
) : CharacterRepository {
    override fun getCharacters(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { db.characterDao.getCharacters() }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = CharacterRemoteMediator(
                api,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { CharacterEntityPagingData ->
            CharacterEntityPagingData.map { characterEntity -> characterEntity.toCharacter() }
        }
    }
}