package tech.dalapenko.pokeapipagingexample.core.database.mapper

interface DboMapper<DBO, MODEL> {

    fun mapToModel(dbo: DBO): MODEL
    fun mapToDbo(model: MODEL): DBO
}