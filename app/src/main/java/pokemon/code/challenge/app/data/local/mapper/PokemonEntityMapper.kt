package pokemon.code.challenge.app.data.local.mapper

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.presentation.model.Pokemon

fun PokemonEntity.toUI(): Pokemon {
    val entity = this
    return Pokemon(
        id = entity.id,
        name = entity.name,
        image = entity.image,
        height = entity.height,
        weight = entity.weight,
        typeOne = entity.typeOne,
        typeTwo = entity.typeTwo,
        favorite = entity.favorite,
        hasDetail = entity.hasDetail
    )
}