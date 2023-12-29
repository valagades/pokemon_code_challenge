package pokemon.code.challenge.app.domain.local.getDetailById

import kotlinx.coroutines.flow.Flow
import pokemon.code.challenge.app.presentation.model.Pokemon

interface GetDetailByIdUseCase {

    operator fun invoke(id: Int): Flow<Pokemon>
}