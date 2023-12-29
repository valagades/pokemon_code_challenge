package pokemon.code.challenge.app.data.remote.model

import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_FIELD
import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_LATITUDE_FIELD
import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_LONGITUDE_FIELD

data class LocationModel(
    val date: String,
    val latitude: Double,
    val longitude: Double
) {
    fun getData() = hashMapOf(
        USER_COLLECTION_LOCATIONS_FIELD to hashMapOf(
            date to hashMapOf(
                USER_COLLECTION_LOCATIONS_LATITUDE_FIELD to latitude,
                USER_COLLECTION_LOCATIONS_LONGITUDE_FIELD to longitude
            )
        )
    )
}