package pokemon.code.challenge.app.domain.remote.getList

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.remote.repository.PokemonRemoteRepository
import javax.inject.Inject

class GetListUseCaseImpl @Inject constructor(private val repository: PokemonRemoteRepository) : GetListUseCase {

    override suspend fun invoke(offset: Int): List<PokemonEntity> {
        return repository.getResults(offset)
    }
}