package tech.dalapenko.pokeapipagingexample.core.database.dao

import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonWithSpriteDbo
import androidx.room.*
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonToPokemonSpriteCrossRefDbo

@Dao
abstract class PokeApiDao {

    @Transaction
    @Query("SELECT * FROM pokemon ORDER BY pokemon_id ASC LIMIT :limit OFFSET :offset")
    abstract suspend fun getPokemonPagedList(
        limit: Int,
        offset: Int
    ): List<PokemonWithSpriteDbo>

    @Query("SELECT COUNT(*) FROM pokemon")
    abstract suspend fun getPokemonItemCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemon(
        pokemonList: List<PokemonDbo>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemonToPokemonSprite(
        releaseGenres: List<PokemonToPokemonSpriteCrossRefDbo>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemonSprite(
        releaseCountries: List<PokemonSpriteDbo>
    )

    @Query("DELETE FROM pokemon")
    abstract suspend fun clearAllPokemon()

    @Transaction
    open suspend fun insertPokemonWithSprite(
        pokemonWithSpriteList: List<PokemonWithSpriteDbo>
    ) {
        insertPokemon(pokemonWithSpriteList.map(PokemonWithSpriteDbo::pokemon))
        insertPokemonSprite(pokemonWithSpriteList.map(PokemonWithSpriteDbo::sprite))
        insertPokemonToPokemonSprite(pokemonWithSpriteList.map(::mapToPokemonToPokemonSpriteDbo))
    }

    @Transaction
    open suspend fun clearAndInsertPokemonWithSprite(
        pokemonWithSpriteList: List<PokemonWithSpriteDbo>
    ) {
        clearAllPokemon()
        insertPokemonWithSprite(pokemonWithSpriteList)
    }

    private fun mapToPokemonToPokemonSpriteDbo(
        pokemonWithSpriteDbo: PokemonWithSpriteDbo
    ): PokemonToPokemonSpriteCrossRefDbo {
        return PokemonToPokemonSpriteCrossRefDbo(
            pokemonId = pokemonWithSpriteDbo.pokemon.id,
            spriteId = pokemonWithSpriteDbo.sprite.id
        )
    }
}
