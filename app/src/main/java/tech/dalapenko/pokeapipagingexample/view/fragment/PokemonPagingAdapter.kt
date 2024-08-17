package tech.dalapenko.pokeapipagingexample.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import tech.dalapenko.pokeapipagingexample.R
import tech.dalapenko.pokeapipagingexample.data.model.Pokemon
import tech.dalapenko.pokeapipagingexample.databinding.PokemonItemBinding

class PokemonPagingAdapter : PagingDataAdapter<Pokemon, PokemonPagingAdapter.PokemonItemViewHolder>(
    SearchResultItemCallback
) {

    class PokemonItemViewHolder(val item: PokemonItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        return PokemonItemViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        val data = getItem(position) ?: return

        with(holder.item) {
            data.sprite.url?.let(cover::loadImage)
            data.name.let(name::setText)
            data.height?.let(height::setPokemonHeight)
            data.weight?.let(weight::setPokemonWeight)
        }
    }
}

private object SearchResultItemCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}

private fun TextView.setPokemonHeight(height: Int) {
    context
        .getString(R.string.height_title, height)
        .let(::setTextAndChangeVisibility)
}

private fun TextView.setPokemonWeight(weight: Int) {
    context
        .getString(R.string.weight_title, weight)
        .let(::setTextAndChangeVisibility)
}

private fun TextView.setTextAndChangeVisibility(text: CharSequence?) {
    isVisible = !text.isNullOrBlank()
    text?.let(::setText)
}

private fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(CenterCrop()))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}
