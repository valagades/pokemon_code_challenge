package pokemon.code.challenge.app.data.remote.repository

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.presentation.model.Pokemon

interface PokemonRemoteRepository {

    suspend fun getResults(offset: Int): List<PokemonEntity>

    suspend fun getDetail(pokemon: Pokemon): PokemonEntity
}