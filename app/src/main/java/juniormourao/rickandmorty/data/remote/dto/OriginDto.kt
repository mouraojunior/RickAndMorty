package juniormourao.rickandmorty.data.remote.dto


import com.google.gson.annotations.SerializedName

data class OriginDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)