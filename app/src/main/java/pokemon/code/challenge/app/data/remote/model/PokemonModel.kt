package pokemon.code.challenge.app.data.remote.model

import com.google.gson.annotations.SerializedName
import pokemon.code.challenge.app.util.POKEMON_RESULT_NAME
import pokemon.code.challenge.app.util.POKEMON_RESULT_URL

data class PokemonModel(
    @SerializedName(POKEMON_RESULT_NAME) val name: String,
    @SerializedName(POKEMON_RESULT_URL) val url: String
)