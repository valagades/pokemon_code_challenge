package pokemon.code.challenge.app.domain.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pokemon.code.challenge.app.data.remote.repository.PokemonRemoteRepository
import pokemon.code.challenge.app.domain.remote.getDetail.GetDetailUseCase
import pokemon.code.challenge.app.domain.remote.getDetail.GetDetailUseCaseImpl
import pokemon.code.challenge.app.domain.remote.getList.GetListUseCase
import pokemon.code.challenge.app.domain.remote.getList.GetListUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseRemoteModule {

    @Provides
    @ViewModelScoped
    fun provideGetList(repository: PokemonRemoteRepository): GetListUseCase {
        return GetListUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetail(repository: PokemonRemoteRepository): GetDetailUseCase {
        return GetDetailUseCaseImpl(repository)
    }
}