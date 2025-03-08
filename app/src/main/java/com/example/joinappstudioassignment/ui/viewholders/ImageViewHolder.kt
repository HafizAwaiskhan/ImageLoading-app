package com.example.joinappstudioassignment.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joinappstudioassignment.R
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.databinding.ImageChildItemBinding
import com.example.joinappstudioassignment.utils.ItemClickListener

class ImageViewHolder(
    private val binding: ImageChildItemBinding,
    private val listener: ItemClickListener,
    private val context: Context,
    private val isFavoritesScreen: Boolean // Flag to handle button text
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageResponse: ImageResponse) {
        Glide.with(context)
            .load(imageResponse.download_url).placeholder(R.drawable.image_place_holder)
            .into(binding.imageView)

        binding.description.text = imageResponse.author

        binding.addOrRemoveFavorites.text = if (isFavoritesScreen) {
            context.getString(R.string.remove_from_favorites)
        } else {
            context.getString(R.string.add_to_favorites)
        }

        binding.addOrRemoveFavorites.setOnClickListener {
            if (isFavoritesScreen) {
                listener.onRemoveFromFavorites(imageResponse)
            } else {
                listener.onAddToFavorites(imageResponse)
            }
        }
    }
}