package pokemon.code.challenge.app.presentation.model

sealed interface PokemonDetailUIState {
    object DetailLoading : PokemonDetailUIState
    data class DetailError(val throwable: Throwable) : PokemonDetailUIState
    data class DetailSuccess(val pokemon: Pokemon) : PokemonDetailUIState
}