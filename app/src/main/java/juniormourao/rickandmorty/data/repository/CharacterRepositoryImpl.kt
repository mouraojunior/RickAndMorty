package juniormourao.rickandmorty.data.repository

import androidx.paging.*
import juniormourao.rickandmorty.data.cache.RickAndMortyDatabase
import juniormourao.rickandmorty.data.paging.CharacterByNameRemoteMediator
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
    override fun getCharactersByName(characterName: String): Flow<PagingData<Character>> {
        val pagingSourceFactory = { db.characterDao.getCharactersByName(characterName) }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = CharacterByNameRemoteMediator(
                api,
                db,
                characterName
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { CharacterEntityPagingData ->
            CharacterEntityPagingData.map { characterEntity -> characterEntity.toCharacter() }
        }
    }
}