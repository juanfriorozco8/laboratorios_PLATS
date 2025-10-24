package uvg.plats.rickandmorty.data.network.util

sealed class NetworkError : Exception() {
    data object NoInternet : NetworkError()
    data object Timeout : NetworkError()
    data class ServerError(val code: Int) : NetworkError()
    data class Unknown(val error: Throwable) : NetworkError()
}