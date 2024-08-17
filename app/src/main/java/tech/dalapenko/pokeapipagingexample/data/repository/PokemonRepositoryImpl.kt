package tech.dalapenko.pokeapipagingexample.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import tech.dalapenko.pokeapipagingexample.data.datasource.local.PokemonPagingDataSource
import tech.dalapenko.pokeapipagingexample.data.datasource.remote.PokemonRemoteMediator
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon

@ExperimentalPagingApi
class PokemonRepositoryImpl(
    private val pagingDataSourceFactory: PokemonPagingDataSource.Factory,
    private val remoteMediator: PokemonRemoteMediator
) : PokemonRepository {

    override fun getPokemonPager(): Pager<Int, Pokemon> {
        return Pager(
            config = PagingConfig(
                pageSize = RELEASE_PAGE_SIZE,
                initialLoadSize = RELEASE_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { pagingDataSourceFactory.create() }
        )
    }
}

private const val RELEASE_PAGE_SIZE = 20