package pokemon.code.challenge.app.data.remote.mapper

import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.remote.model.PokemonDetailModel
import pokemon.code.challenge.app.data.remote.model.PokemonModel
import pokemon.code.challenge.app.presentation.model.Pokemon
import pokemon.code.challenge.app.util.getPokemonId
import pokemon.code.challenge.app.util.orDefault
import pokemon.code.challenge.app.util.toCamelCase
import pokemon.code.challenge.app.util.toPokemonImageUrl

fun PokemonModel.toEntity(): PokemonEntity {
    val id = this.url.getPokemonId().toIntOrNull().orDefault()
    return PokemonEntity(
        id = id,
        name = this.name.toCamelCase(),
        image = id.toPokemonImageUrl(),
        height = 0.0,
        weight = 0.0,
        typeOne = "",
        typeTwo = "",
        favorite = false,
        hasDetail = false
    )
}

fun PokemonDetailModel?.toEntity(pokemon: Pokemon): PokemonEntity {
    val id = pokemon.id
    return PokemonEntity(
        id = id,
        name = this?.name.orEmpty().toCamelCase(),
        image = id.toPokemonImageUrl(),
        height = this?.height?.toDouble().orDefault(),
        weight = this?.weight?.toDouble().orDefault(),
        typeOne = this?.types?.getOrNull(0)?.type?.name.orEmpty().toCamelCase(),
        typeTwo = this?.types?.getOrNull(1)?.type?.name.orEmpty().toCamelCase(),
        favorite = pokemon.favorite,
        hasDetail = true
    )
}