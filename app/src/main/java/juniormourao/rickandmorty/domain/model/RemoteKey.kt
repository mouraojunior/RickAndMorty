package juniormourao.rickandmorty.domain.model

data class RemoteKey(
    val id: Int,
    val nextKey: Int,
    val isEndReached: Boolean
)