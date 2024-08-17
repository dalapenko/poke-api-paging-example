package tech.dalapenko.pokeapipagingexample.data.mapper.local

import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.mapper.DboMapper
import tech.dalapenko.pokeapipagingexample.data.model.PokemonSprite
import javax.inject.Inject

internal class PokemonSpriteDboMapper @Inject constructor()
    : DboMapper<PokemonSpriteDbo, PokemonSprite> {

    override fun mapToModel(dbo: PokemonSpriteDbo): PokemonSprite {
        return PokemonSprite(
            id = dbo.id,
            url = dbo.url
        )
    }

    override fun mapToDbo(model: PokemonSprite): PokemonSpriteDbo {
        return PokemonSpriteDbo(
            id = model.id,
            url = model.url
        )
    }
}