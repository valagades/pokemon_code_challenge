package pokemon.code.challenge.app.presentation.model

sealed interface PokemonUIState {

    object Loading : PokemonUIState
    data class Complete(val pokemons: List<Pokemon>) : PokemonUIState
    data class Error(val throwable: Throwable) : PokemonUIState
}