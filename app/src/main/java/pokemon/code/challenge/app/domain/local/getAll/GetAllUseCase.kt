package pokemon.code.challenge.app.domain.local.getAll

import kotlinx.coroutines.flow.Flow
import pokemon.code.challenge.app.presentation.model.Pokemon

interface GetAllUseCase {

    operator fun invoke(): Flow<List<Pokemon>>
}