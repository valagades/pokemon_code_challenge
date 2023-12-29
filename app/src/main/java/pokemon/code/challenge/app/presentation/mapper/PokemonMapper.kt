package pokemon.code.challenge.app.presentation.mapper

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.presentation.model.Pokemon

fun Pokemon.toEntity(): PokemonEntity {
    val pokemon = this
    return PokemonEntity(
        id = pokemon.id,
        name = pokemon.name,
        image = pokemon.image,
        height = pokemon.height,
        weight = pokemon.weight,
        typeOne = pokemon.typeOne,
        typeTwo = pokemon.typeTwo,
        favorite = pokemon.favorite,
        hasDetail = pokemon.hasDetail
    )
}