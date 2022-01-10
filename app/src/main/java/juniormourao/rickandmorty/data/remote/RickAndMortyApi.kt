package juniormourao.rickandmorty.data.remote

import juniormourao.rickandmorty.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponseDto

    companion object {
        const val BASE_URL_API = "https://rickandmortyapi.com/api/"
    }
}