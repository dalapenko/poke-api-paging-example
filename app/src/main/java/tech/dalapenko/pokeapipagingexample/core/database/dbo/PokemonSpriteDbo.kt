package tech.dalapenko.pokeapipagingexample.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.dalapenko.pokeapipagingexample.core.database.Table

@Entity(tableName = Table.POKEMON_SPRITES)
data class PokemonSpriteDbo(
    @PrimaryKey @ColumnInfo(name="sprite_id") val id: Int,
    @ColumnInfo(name = "url") val url: String?
)
