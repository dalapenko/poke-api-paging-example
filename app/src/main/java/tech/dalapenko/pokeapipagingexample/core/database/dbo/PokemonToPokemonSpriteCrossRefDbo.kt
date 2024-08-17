package tech.dalapenko.pokeapipagingexample.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import tech.dalapenko.pokeapipagingexample.core.database.Table

@Entity(
    tableName = Table.POKEMON_TO_POKEMON_SPRITE,
    primaryKeys = ["pokemon_id", "sprite_id"],
    indices = [Index("sprite_id")]
)
data class PokemonToPokemonSpriteCrossRefDbo(
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "sprite_id") val spriteId: Int
)