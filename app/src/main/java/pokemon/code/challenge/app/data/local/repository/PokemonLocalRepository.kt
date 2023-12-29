package pokemon.code.challenge.app.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pokemon.code.challenge.app.data.local.dao.PokemonDao
import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.data.local.mapper.toUI
import pokemon.code.challenge.app.presentation.model.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalRepository @Inject constructor(private val pokemonDao: PokemonDao) {

    val pokemons: Flow<List<Pokemon>> =
        pokemonDao.getAll().map { items ->
            items.map {
                it.toUI()
            }
        }

    fun getById(id: Int): Flow<Pokemon> = pokemonDao.getById(id).map { it.toUI() }

    suspend fun insert(vararg pokemons: PokemonEntity) {
        //val insertArray = pokemons.map { it.toEntity()}
        pokemonDao.insert(*pokemons)
    }

    suspend fun update(pokemon: PokemonEntity) {
        pokemonDao.update(pokemon)
    }
}