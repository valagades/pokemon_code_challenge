package pokemon.code.challenge.app.domain.remote.getDetail

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.presentation.model.Pokemon

interface GetDetailUseCase {

    suspend operator fun invoke(pokemon: Pokemon): PokemonEntity
}