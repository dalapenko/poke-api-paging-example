package tech.dalapenko.pokemonpagingexample.data.mapper

import tech.dalapenko.pokemonpagingexample.GetPokemonQuery
import tech.dalapenko.pokemonpagingexample.data.model.Pokemon
import tech.dalapenko.pokemonpagingexample.data.network.mapper.DtoMapper
import javax.inject.Inject

internal class ReleaseDtoMapper @Inject constructor() : DtoMapper<GetPokemonQuery.Pokemon_v2_pokemon, Pokemon> {

    override fun mapToModel(dto: GetPokemonQuery.Pokemon_v2_pokemon): Pokemon {
        return Pokemon(
            id = dto.id,
            name = dto.name,
            weight = dto.weight,
            height = dto.height,
            imgUrl = null
        )
    }

    override fun mapToDto(model: Pokemon): GetPokemonQuery.Pokemon_v2_pokemon {
        throw NotImplementedError()
    }
}