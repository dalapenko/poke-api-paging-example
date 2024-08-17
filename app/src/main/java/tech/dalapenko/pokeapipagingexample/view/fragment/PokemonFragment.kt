package tech.dalapenko.pokeapipagingexample.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.dalapenko.pokeapipagingexample.R
import tech.dalapenko.pokeapipagingexample.databinding.PokemonListFragmentBinding
import tech.dalapenko.pokeapipagingexample.view.viewmodel.PokemonListViewModel

@AndroidEntryPoint
class PokemonFragment : Fragment(R.layout.pokemon_list_fragment) {

    private var _binding: PokemonListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonListViewModel by viewModels()

    private val pokemonPagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PokemonPagingAdapter().also { adapter ->
            adapter.withLoadStateFooter(PokemonPagingLoadStateAdapter())
            adapter.addLoadStateListener { loadState ->
                val isInitializing = loadState.refresh is LoadState.Loading && adapter.itemCount == 0
                binding.loader.isVisible = isInitializing
                binding.content.isVisible = !isInitializing
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        binding.content.layoutManager = LinearLayoutManager(context)
        binding.content.adapter = pokemonPagingAdapter
        binding.refresh.setOnRefreshListener(::refreshData)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataStateFlow.collectLatest(pokemonPagingAdapter::submitData)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshData() {
        pokemonPagingAdapter.refresh()
        binding.refresh.isRefreshing = false
    }
}
