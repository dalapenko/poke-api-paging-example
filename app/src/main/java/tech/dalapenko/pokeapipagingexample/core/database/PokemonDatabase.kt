package tech.dalapenko.pokeapipagingexample.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.dalapenko.pokeapipagingexample.core.database.dao.PokeApiDao
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonToPokemonSpriteCrossRefDbo

@Database(
    entities = [
        PokemonDbo::class,
        PokemonSpriteDbo::class,
        PokemonToPokemonSpriteCrossRefDbo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokeApiDatabase : RoomDatabase() {

    abstract fun getPokeApiDao(): PokeApiDao

    companion object {

        @Volatile
        private var instance: PokeApiDatabase? = null

        fun getInstance(context: Context): PokeApiDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PokeApiDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }

            return instance!!
        }
    }
}

private const val DATABASE_NAME = "poke_api_database"