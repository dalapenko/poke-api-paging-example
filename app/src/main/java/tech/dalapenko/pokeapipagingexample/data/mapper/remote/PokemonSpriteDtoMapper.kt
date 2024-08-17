package tech.dalapenko.pokeapipagingexample.data.mapper.remote

import tech.dalapenko.pokeapipagingexample.GetPokemonQuery
import tech.dalapenko.pokeapipagingexample.core.network.mapper.DtoMapper
import tech.dalapenko.pokeapipagingexample.data.model.PokemonSprite
import javax.inject.Inject

class PokemonSpriteDtoMapper @Inject constructor()
    : DtoMapper<GetPokemonQuery.PokemonSpriteDto, PokemonSprite> {
    override fun mapToModel(dto: GetPokemonQuery.PokemonSpriteDto): PokemonSprite {
        return PokemonSprite(
            id = dto.id,
            url = (dto.spriteUrl as? String).orEmpty()
        )
    }

    override fun mapToDto(model: PokemonSprite): GetPokemonQuery.PokemonSpriteDto {
        throw NotImplementedError()
    }
}