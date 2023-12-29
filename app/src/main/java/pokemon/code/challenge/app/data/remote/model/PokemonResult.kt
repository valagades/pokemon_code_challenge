package pokemon.code.challenge.app.data.remote.model

import com.google.gson.annotations.SerializedName
import pokemon.code.challenge.app.util.POKEMON_RESULT

data class PokemonResult(
    @SerializedName(POKEMON_RESULT) val results: List<PokemonModel>
)