package pokemon.code.challenge.app.domain.local.insert

import pokemon.code.challenge.app.data.local.entity.PokemonEntity

interface InsertUseCase {

    suspend operator fun invoke(vararg pokemons: PokemonEntity)
}