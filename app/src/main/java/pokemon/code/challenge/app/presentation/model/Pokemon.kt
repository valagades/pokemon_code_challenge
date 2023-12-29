package pokemon.code.challenge.app.presentation.model

data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val height: Double,
    val weight: Double,
    val typeOne: String,
    val typeTwo: String,
    val favorite: Boolean,
    val hasDetail: Boolean
)