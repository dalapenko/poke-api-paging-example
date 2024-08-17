package tech.dalapenko.pokeapipagingexample.core.database.dbo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PokemonWithSpriteDbo(
    @Embedded val pokemon: PokemonDbo,
    @Relation(
        parentColumn = "pokemon_id",
        entityColumn = "sprite_id",
        associateBy = Junction(value = PokemonToPokemonSpriteCrossRefDbo::class)
    )
    val sprite : PokemonSpriteDbo,
)