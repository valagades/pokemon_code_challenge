package pokemon.code.challenge.app.domain.local.getDetailById

import kotlinx.coroutines.flow.Flow
import pokemon.code.challenge.app.data.local.repository.PokemonLocalRepository
import pokemon.code.challenge.app.presentation.model.Pokemon
import javax.inject.Inject

class GetDetailByIdUseCaseImpl @Inject constructor(private val repository: PokemonLocalRepository) : GetDetailByIdUseCase {

    override fun invoke(id: Int): Flow<Pokemon> {
        return repository.getById(id)
    }
}