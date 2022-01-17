package juniormourao.rickandmorty.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import juniormourao.rickandmorty.data.cache.RickAndMortyDatabase
import juniormourao.rickandmorty.data.remote.RickAndMortyApi
import juniormourao.rickandmorty.data.remote.RickAndMortyApi.Companion.BASE_URL_API
import juniormourao.rickandmorty.data.repository.CharacterRepositoryImpl
import juniormourao.rickandmorty.domain.repository.CharacterRepository
import juniormourao.rickandmorty.domain.use_case.GetCharacters
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickAndMortyModule {
    @Singleton
    @Provides
    fun provideGetCharactersUseCase(
        getCharactersRepository: CharacterRepository
    ): GetCharacters = GetCharacters(getCharactersRepository)

    @Singleton
    @Provides
    @ExperimentalPagingApi
    fun provideCharacterRepository(
        api: RickAndMortyApi,
        db: RickAndMortyDatabase
    ): CharacterRepository = CharacterRepositoryImpl(api, db)

    @Singleton
    @Provides
    fun provideRickAndMortyApi(
    ): RickAndMortyApi =
        Retrofit.Builder().baseUrl(BASE_URL_API).addConverterFactory(GsonConverterFactory.create())
            .build().create(RickAndMortyApi::class.java)

    @Singleton
    @Provides
    fun provideRickAndMortyDatabase(
        app: Application
    ): RickAndMortyDatabase =
        Room.databaseBuilder(app, RickAndMortyDatabase::class.java, "rickAndMortyDb").build()
}