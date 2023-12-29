package pokemon.code.challenge.app.domain.local.getAll

import kotlinx.coroutines.flow.Flow
import pokemon.code.challenge.app.data.local.repository.PokemonLocalRepository
import pokemon.code.challenge.app.presentation.model.Pokemon
import javax.inject.Inject

class GetAllUseCaseImpl @Inject constructor(private val repository: PokemonLocalRepository) : GetAllUseCase {

    override fun invoke(): Flow<List<Pokemon>> {
        return repository.pokemons
    }
}