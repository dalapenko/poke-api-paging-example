package tech.dalapenko.pokeapipagingexample.data.mapper.remote

import tech.dalapenko.pokeapipagingexample.GetPokemonQuery
import tech.dalapenko.pokeapipagingexample.core.network.mapper.DtoMapper
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon
import tech.dalapenko.pokeapipagingexample.data.model.PokemonSprite

class PokemonDtoMapper(
    private val pokemonSpriteDtoMapper: DtoMapper<GetPokemonQuery.PokemonSpriteDto, PokemonSprite>
) : DtoMapper<GetPokemonQuery.PokemonDto, Pokemon> {
    override fun mapToModel(dto: GetPokemonQuery.PokemonDto): Pokemon {
        return Pokemon(
            id = dto.id,
            name = dto.name,
            weight = dto.weight,
            height = dto.height,
            sprite = dto.pokemonSpriteDto.first().let(pokemonSpriteDtoMapper::mapToModel)
        )
    }

    override fun mapToDto(model: Pokemon): GetPokemonQuery.PokemonDto {
        throw NotImplementedError()
    }
}