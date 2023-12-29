package pokemon.code.challenge.app.data.remote.repository

interface LocationRemoteRepository {

    suspend fun sendLocation(user: String, date: String, latitude: Double, longitude: Double)
}