package tech.dalapenko.pokeapipagingexample.core.network.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providePokemonApolloClient(
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(APOLLO_API_BASE_URL)
            .build()
    }
}

private const val APOLLO_API_BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"
