package tech.dalapenko.pokemonpagingexample.data.model

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int?,
    val height: Int?,
    val imgUrl: String?
)
