package pokemon.code.challenge.app.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pokemon.code.challenge.app.data.local.dao.PokemonDao
import pokemon.code.challenge.app.data.local.database.PokemonAppDatabase
import pokemon.code.challenge.app.util.POKEMON_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providePokemonAppDatabase(@ApplicationContext context: Context): PokemonAppDatabase {
        return Room.databaseBuilder(context, PokemonAppDatabase::class.java, POKEMON_DATABASE_NAME).build()
    }

    @Provides
    fun providePokemonDao(pokemonAppDatabase: PokemonAppDatabase): PokemonDao {
        return pokemonAppDatabase.pokemonDao()
    }
}