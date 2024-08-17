package tech.dalapenko.pokeapipagingexample.core.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.dalapenko.pokeapipagingexample.core.database.dao.PokeApiDao
import tech.dalapenko.pokeapipagingexample.core.database.PokeApiDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PokeApiDatabase {
        return PokeApiDatabase.getInstance(context)
    }

    @Provides
    fun providePokeApiDao(
        database: PokeApiDatabase
    ): PokeApiDao {
        return database.getPokeApiDao()
    }
}