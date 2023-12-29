package pokemon.code.challenge.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pokemon.code.challenge.app.data.local.dao.PokemonDao
import pokemon.code.challenge.app.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonAppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}