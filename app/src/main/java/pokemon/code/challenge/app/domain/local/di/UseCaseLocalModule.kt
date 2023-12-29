package pokemon.code.challenge.app.domain.local.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pokemon.code.challenge.app.data.local.repository.PokemonLocalRepository
import pokemon.code.challenge.app.domain.local.getAll.GetAllUseCase
import pokemon.code.challenge.app.domain.local.getAll.GetAllUseCaseImpl
import pokemon.code.challenge.app.domain.local.getDetailById.GetDetailByIdUseCase
import pokemon.code.challenge.app.domain.local.getDetailById.GetDetailByIdUseCaseImpl
import pokemon.code.challenge.app.domain.local.insert.InsertUseCase
import pokemon.code.challenge.app.domain.local.insert.InsertUseCaseImpl
import pokemon.code.challenge.app.domain.local.update.UpdateUseCase
import pokemon.code.challenge.app.domain.local.update.UpdateUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseLocalModule {

    @Provides
    @ViewModelScoped
    fun provideGetAll(repository: PokemonLocalRepository): GetAllUseCase {
        return GetAllUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetById(repository: PokemonLocalRepository): GetDetailByIdUseCase {
        return GetDetailByIdUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsert(repository: PokemonLocalRepository): InsertUseCase {
        return InsertUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdate(repository: PokemonLocalRepository): UpdateUseCase {
        return UpdateUseCaseImpl(repository)
    }
}