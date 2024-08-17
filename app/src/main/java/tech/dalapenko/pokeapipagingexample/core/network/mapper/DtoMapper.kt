package tech.dalapenko.pokeapipagingexample.core.network.mapper

interface DtoMapper<DTO, MODEL> {

    fun mapToModel(dto: DTO): MODEL
    fun mapToDto(model: MODEL): DTO
}