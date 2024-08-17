package tech.dalapenko.pokeapipagingexample.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import tech.dalapenko.pokeapipagingexample.data.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pagingDataStateFlow = pokemonRepository
        .getPokemonPager()
        .flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}