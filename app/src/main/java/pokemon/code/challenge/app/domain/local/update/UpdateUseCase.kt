package pokemon.code.challenge.app.domain.local.update

import pokemon.code.challenge.app.data.local.entity.PokemonEntity

interface UpdateUseCase {

    suspend operator fun invoke(pokemon: PokemonEntity)
}