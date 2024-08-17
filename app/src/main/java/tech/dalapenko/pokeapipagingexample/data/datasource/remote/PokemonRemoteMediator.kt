package tech.dalapenko.pokeapipagingexample.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.apollographql.apollo.ApolloClient
import tech.dalapenko.pokeapipagingexample.GetPokemonQuery
import tech.dalapenko.pokeapipagingexample.core.database.dao.PokeApiDao
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonWithSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.mapper.DboMapper
import tech.dalapenko.pokeapipagingexample.core.network.mapper.DtoMapper
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val pokeApiDao: PokeApiDao,
    private val apolloClient: ApolloClient,
    private val dboMapper: DboMapper<PokemonWithSpriteDbo, Pokemon>,
    private val dtoMapper: DtoMapper<GetPokemonQuery.PokemonDto, Pokemon>
) : RemoteMediator<Int, Pokemon>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> 0
            LoadType.APPEND -> pokeApiDao.getPokemonItemCount()
        } ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = if (loadType == LoadType.REFRESH) state.config.initialLoadSize else state.config.pageSize
        val mediatorResult = try {
            val data = apolloClient
                .query(GetPokemonQuery(limit = limit, offset = loadKey))
                .execute()
                .dataOrThrow()

            val pokemonWithSpritesDboList = data.pokemonDto
                .map(dtoMapper::mapToModel)
                .map(dboMapper::mapToDbo)

            if (loadType == LoadType.REFRESH) {
                pokeApiDao.clearAndInsertPokemonWithSprite(pokemonWithSpritesDboList)
            } else {
                pokeApiDao.insertPokemonWithSprite(pokemonWithSpritesDboList)
            }

            val isEndReached = data.pokemonDto.size < limit
            MediatorResult.Success(
                endOfPaginationReached = isEndReached
            )
        } catch (e: Throwable) {
            MediatorResult.Error(e)
        }

        return mediatorResult
    }
}