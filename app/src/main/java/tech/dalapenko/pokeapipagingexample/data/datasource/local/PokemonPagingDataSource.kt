package tech.dalapenko.pokeapipagingexample.data.datasource.local

import tech.dalapenko.pokeapipagingexample.core.database.PokeApiDatabase
import tech.dalapenko.pokeapipagingexample.core.database.Table
import tech.dalapenko.pokeapipagingexample.core.database.dao.PokeApiDao
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonWithSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.mapper.DboMapper
import tech.dalapenko.pokeapipagingexample.core.database.paging.DatabasePagingSource
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon

class PokemonPagingDataSource(
    database: PokeApiDatabase,
    private val pokemonDao: PokeApiDao,
    private val dboMapper: DboMapper<PokemonWithSpriteDbo, Pokemon>
) : DatabasePagingSource<Pokemon>(database, arrayOf(Table.POKEMON)) {

    override suspend fun fetchPageFromDatabase(limit: Int, offset: Int): List<Pokemon> {
        return pokemonDao.getPokemonPagedList(limit, offset).map(dboMapper::mapToModel)
    }

    override suspend fun getDatabaseItemCount(): Int {
        return pokemonDao.getPokemonItemCount()
    }

    interface Factory {
        fun create(): PokemonPagingDataSource
    }
}