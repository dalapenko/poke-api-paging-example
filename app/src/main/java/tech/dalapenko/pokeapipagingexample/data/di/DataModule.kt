package tech.dalapenko.pokeapipagingexample.data.di

import androidx.paging.ExperimentalPagingApi
import com.apollographql.apollo.ApolloClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.pokeapipagingexample.GetPokemonQuery
import tech.dalapenko.pokeapipagingexample.core.database.PokeApiDatabase
import tech.dalapenko.pokeapipagingexample.core.database.dao.PokeApiDao
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonWithSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.mapper.DboMapper
import tech.dalapenko.pokeapipagingexample.core.network.mapper.DtoMapper
import tech.dalapenko.pokeapipagingexample.data.datasource.local.PokemonPagingDataSource
import tech.dalapenko.pokeapipagingexample.data.datasource.remote.PokemonRemoteMediator
import tech.dalapenko.pokeapipagingexample.data.mapper.local.PokemonSpriteDboMapper
import tech.dalapenko.pokeapipagingexample.data.mapper.local.PokemonWithSpriteDboMapper
import tech.dalapenko.pokeapipagingexample.data.mapper.remote.PokemonDtoMapper
import tech.dalapenko.pokeapipagingexample.data.mapper.remote.PokemonSpriteDtoMapper
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon
import tech.dalapenko.pokeapipagingexample.data.model.PokemonSprite
import tech.dalapenko.pokeapipagingexample.data.repository.PokemonRepository
import tech.dalapenko.pokeapipagingexample.data.repository.PokemonRepositoryImpl

@Module(includes = [InternalDataModule::class])
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun providePagingDataSourceFactory(
        database: PokeApiDatabase,
        pokemonDao: PokeApiDao,
        dboMapper: DboMapper<PokemonWithSpriteDbo, Pokemon>
    ): PokemonPagingDataSource.Factory {
        return object : PokemonPagingDataSource.Factory {
            override fun create(): PokemonPagingDataSource {
                return PokemonPagingDataSource(database, pokemonDao, dboMapper)
            }
        }
    }

    @ExperimentalPagingApi
    @Provides
    fun provideRemoteMediatorFactory(
        pokeApiDao: PokeApiDao,
        apolloClient: ApolloClient,
        dboMapper: DboMapper<PokemonWithSpriteDbo, Pokemon>,
        dtoMapper: DtoMapper<GetPokemonQuery.PokemonDto, Pokemon>
    ): PokemonRemoteMediator {
        return PokemonRemoteMediator(pokeApiDao, apolloClient, dboMapper, dtoMapper)
    }

    @ExperimentalPagingApi
    @Provides
    fun providePokemonRepository(
        pagingDataSourceFactory: PokemonPagingDataSource.Factory,
        remoteMediator: PokemonRemoteMediator
    ): PokemonRepository {
        return PokemonRepositoryImpl(pagingDataSourceFactory, remoteMediator)
    }
}

@Module(includes = [InternalDataModule.Binding::class])
@InstallIn(ViewModelComponent::class)
object InternalDataModule {

    @Provides
    fun providePokemonDtoMapper(
        pokemonSpriteDtoMapper: DtoMapper<GetPokemonQuery.PokemonSpriteDto, PokemonSprite>
    ): DtoMapper<GetPokemonQuery.PokemonDto, Pokemon> {
        return PokemonDtoMapper(pokemonSpriteDtoMapper)
    }

    @Provides
    fun providePokemonDboMapper(
        pokemonSpriteDboMapper: DboMapper<PokemonSpriteDbo, PokemonSprite>
    ): DboMapper<PokemonWithSpriteDbo, Pokemon> {
        return PokemonWithSpriteDboMapper(pokemonSpriteDboMapper)
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    internal interface Binding {

        @Binds
        fun bindPokemonSpriteDboMapper(
            dboMapper: PokemonSpriteDboMapper
        ): DboMapper<PokemonSpriteDbo, PokemonSprite>

        @Binds
        fun bindPokemonSpriteDtoMapper(
            dtoMapper: PokemonSpriteDtoMapper
        ): DtoMapper<GetPokemonQuery.PokemonSpriteDto, PokemonSprite>
    }
}