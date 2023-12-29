package pokemon.code.challenge.app.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import pokemon.code.challenge.app.data.remote.model.LocationModel
import pokemon.code.challenge.app.util.USER_COLLECTION

class LocationRemoteRepositoryImpl : LocationRemoteRepository {

    override suspend fun sendLocation(user: String, date: String, latitude: Double, longitude: Double) {
        val model = LocationModel(date, latitude, longitude)
        FirebaseFirestore.getInstance()
            .collection(USER_COLLECTION)
            .document(user)
            .set(model.getData(), SetOptions.merge())
    }
}