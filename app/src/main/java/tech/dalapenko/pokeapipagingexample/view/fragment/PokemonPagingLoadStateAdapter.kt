package tech.dalapenko.pokeapipagingexample.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.pokeapipagingexample.R
import tech.dalapenko.pokeapipagingexample.databinding.PokemonItemLoadStateBinding

class PokemonPagingLoadStateAdapter
    : LoadStateAdapter<PokemonPagingLoadStateAdapter.LoadStateItemViewHolder>() {

    override fun onBindViewHolder(
        holder: LoadStateItemViewHolder,
        loadState: LoadState
    ) = with(holder.binding) {
        loader.isVisible = loadState is LoadState.Loading
        error.isVisible = loadState is LoadState.Error

        if (loadState !is LoadState.Error) return

        loadState.error.localizedMessage
            ?.let(error::setText)
            ?: error.setText(R.string.error_message)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateItemViewHolder {
        return LoadStateItemViewHolder(
            PokemonItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class LoadStateItemViewHolder(
        val binding: PokemonItemLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
