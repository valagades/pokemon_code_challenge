package pokemon.code.challenge.app.domain.local.update

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.local.repository.PokemonLocalRepository
import javax.inject.Inject

class UpdateUseCaseImpl @Inject constructor(private val repository: PokemonLocalRepository) : UpdateUseCase {

    override suspend fun invoke(pokemon: PokemonEntity) {
        repository.update(pokemon)
    }
}