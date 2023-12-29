package pokemon.code.challenge.app.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokemon.code.challenge.app.data.remote.api.PokemonService
import pokemon.code.challenge.app.data.remote.repository.LocationRemoteRepository
import pokemon.code.challenge.app.data.remote.repository.LocationRemoteRepositoryImpl
import pokemon.code.challenge.app.data.remote.repository.PokemonRemoteRepository
import pokemon.code.challenge.app.data.remote.repository.PokemonRemoteRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokemonRemoteModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonRemoteRepository(service: PokemonService): PokemonRemoteRepository {
        return PokemonRemoteRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun provideLocationRemoteRepository(): LocationRemoteRepository {
        return LocationRemoteRepositoryImpl()
    }
}