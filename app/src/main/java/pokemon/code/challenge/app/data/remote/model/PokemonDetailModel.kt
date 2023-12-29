package pokemon.code.challenge.app.data.remote.model

import com.google.gson.annotations.SerializedName
import pokemon.code.challenge.app.util.POKEMON_DETAIL_RESULT_HEIGHT
import pokemon.code.challenge.app.util.POKEMON_DETAIL_RESULT_NAME
import pokemon.code.challenge.app.util.POKEMON_DETAIL_RESULT_TYPE
import pokemon.code.challenge.app.util.POKEMON_DETAIL_RESULT_TYPES
import pokemon.code.challenge.app.util.POKEMON_DETAIL_RESULT_WEIGHT

data class PokemonDetailModel(
    @SerializedName(POKEMON_DETAIL_RESULT_NAME) val name: String,
    @SerializedName(POKEMON_DETAIL_RESULT_HEIGHT) val height: Int,
    @SerializedName(POKEMON_DETAIL_RESULT_WEIGHT) val weight: Int,
    @SerializedName(POKEMON_DETAIL_RESULT_TYPES) val types: List<PokemonDetailTypes>
)

data class PokemonDetailTypes(
    @SerializedName(POKEMON_DETAIL_RESULT_TYPE) val type: PokemonDetailType
)

data class PokemonDetailType(
    @SerializedName(POKEMON_DETAIL_RESULT_NAME) val name: String
)