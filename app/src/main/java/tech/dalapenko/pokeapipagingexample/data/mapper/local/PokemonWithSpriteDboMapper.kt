package tech.dalapenko.pokeapipagingexample.data.mapper.local

import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.dbo.PokemonWithSpriteDbo
import tech.dalapenko.pokeapipagingexample.core.database.mapper.DboMapper
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon
import tech.dalapenko.pokeapipagingexample.data.model.PokemonSprite

internal class PokemonWithSpriteDboMapper(
    private val pokemonSpriteDboMapper: DboMapper<PokemonSpriteDbo, PokemonSprite>
) : DboMapper<PokemonWithSpriteDbo, Pokemon> {

    override fun mapToModel(dbo: PokemonWithSpriteDbo): Pokemon {
        return Pokemon(
            id = dbo.pokemon.id,
            name = dbo.pokemon.name,
            weight = dbo.pokemon.weight,
            height = dbo.pokemon.height,
            sprite = dbo.sprite.let(pokemonSpriteDboMapper::mapToModel)
        )
    }

    override fun mapToDbo(model: Pokemon): PokemonWithSpriteDbo {
        return PokemonWithSpriteDbo(
            pokemon = PokemonDbo(
                id = model.id,
                name = model.name,
                height = model.height,
                weight = model.weight
            ),
            sprite = model.sprite.let(pokemonSpriteDboMapper::mapToDbo)
        )
    }
}