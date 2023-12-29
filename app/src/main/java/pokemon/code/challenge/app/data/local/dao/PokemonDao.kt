package pokemon.code.challenge.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pokemon.code.challenge.app.data.local.entity.PokemonEntity
import pokemon.code.challenge.app.util.POKEMON_ENTITY
import pokemon.code.challenge.app.util.POKEMON_ENTITY_COLUMN_ID

@Dao
interface PokemonDao {

    @Query("SELECT * FROM $POKEMON_ENTITY")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM $POKEMON_ENTITY WHERE $POKEMON_ENTITY_COLUMN_ID=:id")
    fun getById(id: Int): Flow<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg pokemons: PokemonEntity)

    @Update
    suspend fun update(pokemon: PokemonEntity)
}