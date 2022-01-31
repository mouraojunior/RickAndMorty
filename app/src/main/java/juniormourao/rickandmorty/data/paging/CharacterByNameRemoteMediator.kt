package juniormourao.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import juniormourao.rickandmorty.data.cache.RickAndMortyDatabase
import juniormourao.rickandmorty.data.cache.entity.CharacterEntity
import juniormourao.rickandmorty.data.cache.entity.RemoteKeyEntity
import juniormourao.rickandmorty.data.remote.RickAndMortyApi
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class CharacterByNameRemoteMediator(
    private val api: RickAndMortyApi,
    private val db: RickAndMortyDatabase,
    private val characterName: String
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.characterDao.countCharacters() > 0) return MediatorResult.Success(false)
                else null
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> getKey()
        }

        try {

            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: 1
            val apiResponse = api.getCharactersByName(page, characterName)

            val characters = apiResponse.results

            val endOfPaginationReached =
                apiResponse.info.next == null || apiResponse.results.isEmpty()

            db.withTransaction {
                val nextKey = page + 1
                db.remoteKeyDao.insertAll(
                    RemoteKeyEntity(
                        0,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.characterDao.insertCharacters(characters.map { it.toCharacterEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeyEntity? {
        return db.remoteKeyDao.getKeys().firstOrNull()
    }
}