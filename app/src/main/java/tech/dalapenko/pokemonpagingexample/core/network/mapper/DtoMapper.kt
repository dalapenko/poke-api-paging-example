package tech.dalapenko.pokemonpagingexample.data.network.mapper

interface DtoMapper<DTO, MODEL> {

    fun mapToModel(dto: DTO): MODEL
    fun mapToDto(model: MODEL): DTO
}