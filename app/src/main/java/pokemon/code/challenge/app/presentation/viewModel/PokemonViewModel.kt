package pokemon.code.challenge.app.presentation.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.remote.model.LocationModel
import pokemon.code.challenge.app.domain.local.getAll.GetAllUseCase
import pokemon.code.challenge.app.domain.local.getDetailById.GetDetailByIdUseCase
import pokemon.code.challenge.app.domain.local.insert.InsertUseCase
import pokemon.code.challenge.app.domain.local.update.UpdateUseCase
import pokemon.code.challenge.app.domain.remote.getDetail.GetDetailUseCase
import pokemon.code.challenge.app.domain.remote.getList.GetListUseCase
import pokemon.code.challenge.app.presentation.model.Pokemon
import pokemon.code.challenge.app.presentation.model.PokemonDetailUIState
import pokemon.code.challenge.app.presentation.model.PokemonDetailUIState.DetailSuccess
import pokemon.code.challenge.app.presentation.model.PokemonUIState
import pokemon.code.challenge.app.presentation.model.PokemonUIState.Complete
import pokemon.code.challenge.app.presentation.model.UIState
import pokemon.code.challenge.app.util.APP_TAG
import pokemon.code.challenge.app.util.USER_COLLECTION
import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_FIELD
import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_LATITUDE_FIELD
import pokemon.code.challenge.app.util.USER_COLLECTION_LOCATIONS_LONGITUDE_FIELD
import pokemon.code.challenge.app.util.orDefault
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    getAllUseCase: GetAllUseCase,
    private val getDetailByIdUseCase: GetDetailByIdUseCase,
    private val insertUseCase: InsertUseCase,
    private val updateUseCase: UpdateUseCase,
    private val getListUseCase: GetListUseCase,
    private val getDetailUseCase: GetDetailUseCase
) : ViewModel() {

    private val uIState = MutableLiveData<UIState>()
    private val locationsModel = MutableLiveData<List<LocationModel>>()
    var listState: LazyListState by mutableStateOf(LazyListState(0, 0))
    val locations: LiveData<List<LocationModel>> = locationsModel
    val pokemonsState: StateFlow<PokemonUIState> =
        getAllUseCase()
            .map(::Complete)
            .catch { PokemonUIState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PokemonUIState.Loading)

    fun update(pokemon: PokemonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUseCase(pokemon)
        }
    }

    fun getDetailById(id: Int): StateFlow<PokemonDetailUIState?> {
        return getDetailByIdUseCase(id)
            .map(::DetailSuccess)
            .catch { PokemonDetailUIState.DetailError(it) }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
    }

    fun getList(offset: Int) {
        uIState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val pokemons = getListUseCase(offset)
            if (pokemons.isNotEmpty()) {
                insertUseCase(*pokemons.toTypedArray())
            }

            uIState.postValue(UIState.Complete)
        }
    }

    fun getDetail(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonToUpdate = getDetailUseCase(pokemon)
            update(pokemonToUpdate)
        }
    }

    fun uIState(): LiveData<UIState> = uIState

    fun updateState(state: UIState) {
        uIState.value = state
    }

    fun startLocationListener(user: String) {
        FirebaseFirestore.getInstance()
            .collection(USER_COLLECTION)
            .document(user)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                try {
                    (documentSnapshot?.data?.get(USER_COLLECTION_LOCATIONS_FIELD) as? HashMap<String, HashMap<String, Double>>)?.let { data ->
                        val locations = mutableListOf<LocationModel>()
                        for ((date, location) in data) {
                            val latitude = location[USER_COLLECTION_LOCATIONS_LATITUDE_FIELD].orDefault()
                            val longitude = location[USER_COLLECTION_LOCATIONS_LONGITUDE_FIELD].orDefault()

                            locations.add(LocationModel(date, latitude, longitude))
                        }

                        locationsModel.value = locations.sortedBy { it.date }
                    }
                } catch (e: Exception) {
                    Log.e(APP_TAG, "Location listener error: ${e.message}")
                }
            }
    }
}