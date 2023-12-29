package pokemon.code.challenge.app.domain.remote.getList

import pokemon.code.challenge.app.data.local.entity.PokemonEntity

interface GetListUseCase {

    suspend operator fun invoke(offset: Int): List<PokemonEntity>
}