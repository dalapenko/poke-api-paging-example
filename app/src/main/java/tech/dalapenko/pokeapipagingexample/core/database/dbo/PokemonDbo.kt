package tech.dalapenko.pokeapipagingexample.core.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.dalapenko.pokeapipagingexample.core.database.Table

@Entity(tableName = Table.POKEMON)
data class PokemonDbo(
    @PrimaryKey @ColumnInfo(name="pokemon_id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "weight") val weight: Int?,
    @ColumnInfo(name = "height") val height: Int?
)