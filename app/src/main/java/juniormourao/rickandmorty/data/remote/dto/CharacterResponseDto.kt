package juniormourao.rickandmorty.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CharacterResponseDto(
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("results")
    val results: List<CharacterDto>
)