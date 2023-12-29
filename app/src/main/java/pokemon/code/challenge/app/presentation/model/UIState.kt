package pokemon.code.challenge.app.presentation.model

sealed interface UIState {
    object Loading : UIState
    object Complete : UIState
    data class Error(val message: String) : UIState
}