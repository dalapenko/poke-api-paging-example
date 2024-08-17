package tech.dalapenko.pokeapipagingexample.data.repository

import androidx.paging.Pager
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon

interface PokemonRepository {

    fun getPokemonPager(): Pager<Int, Pokemon>
}