package pokemon.code.challenge.app.domain.remote.getDetail

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.remote.repository.PokemonRemoteRepository
import pokemon.code.challenge.app.presentation.model.Pokemon
import javax.inject.Inject

class GetDetailUseCaseImpl @Inject constructor(private val repository: PokemonRemoteRepository) : GetDetailUseCase {

    override suspend fun invoke(pokemon: Pokemon): PokemonEntity {
        return repository.getDetail(pokemon)
    }
}