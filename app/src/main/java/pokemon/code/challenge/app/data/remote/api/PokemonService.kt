package pokemon.code.challenge.app.data.remote.api

import pokemon.code.challenge.app.data.remote.model.PokemonDetailModel
import pokemon.code.challenge.app.data.remote.model.PokemonResult
import pokemon.code.challenge.app.util.POKEMON_GET
import pokemon.code.challenge.app.util.POKEMON_GET_DETAIL
import pokemon.code.challenge.app.util.POKEMON_GET_DETAIL_PATH_ID
import pokemon.code.challenge.app.util.POKEMON_GET_QUERY_LIMIT
import pokemon.code.challenge.app.util.POKEMON_GET_QUERY_OFFSET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET(POKEMON_GET)
    suspend fun getResults(
        @Query(POKEMON_GET_QUERY_OFFSET) offset: Int,
        @Query(POKEMON_GET_QUERY_LIMIT) limit: Int = 25): Response<PokemonResult>

    @GET(POKEMON_GET_DETAIL)
    suspend fun getDetail(@Path(POKEMON_GET_DETAIL_PATH_ID) id: Int): Response<PokemonDetailModel>
}