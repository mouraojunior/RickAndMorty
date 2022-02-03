package juniormourao.rickandmorty.data.remote

import juniormourao.rickandmorty.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page")
        page: Int
    ): CharacterResponseDto

    @GET("character/")
    suspend fun getCharactersByName(
        @Query("page")
        page: Int,
        @Query("name")
        characterName: String
    ): CharacterResponseDto

    companion object {
        const val BASE_URL_API = "https://rickandmortyapi.com/api/"
    }
}