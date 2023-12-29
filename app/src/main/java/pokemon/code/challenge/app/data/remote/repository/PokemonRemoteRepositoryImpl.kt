package pokemon.code.challenge.app.data.remote.repository

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.remote.api.PokemonService
import pokemon.code.challenge.app.data.remote.mapper.toEntity
import pokemon.code.challenge.app.presentation.model.Pokemon
import javax.inject.Inject

class PokemonRemoteRepositoryImpl @Inject constructor(private val service: PokemonService) : PokemonRemoteRepository {

    override suspend fun getResults(offset: Int): List<PokemonEntity> {
        return service.getResults(offset).body()?.results?.map { it.toEntity() } ?: emptyList()
    }

    override suspend fun getDetail(pokemon: Pokemon): PokemonEntity {
        return service.getDetail(pokemon.id).body().toEntity(pokemon)
    }
}