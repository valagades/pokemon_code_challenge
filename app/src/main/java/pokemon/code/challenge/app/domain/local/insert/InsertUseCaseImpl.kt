package pokemon.code.challenge.app.domain.local.insert

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.local.repository.PokemonLocalRepository
import javax.inject.Inject

class InsertUseCaseImpl @Inject constructor(private val repository: PokemonLocalRepository) : InsertUseCase {

    override suspend fun invoke(vararg pokemons: PokemonEntity) {
        repository.insert(*pokemons)
    }
}