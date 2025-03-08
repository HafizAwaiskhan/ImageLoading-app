package com.example.joinappstudioassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.databinding.ImageChildItemBinding
import com.example.joinappstudioassignment.ui.viewholders.ImageViewHolder
import com.example.joinappstudioassignment.utils.ItemClickListener


class ImageAdapter(
    private val listener: ItemClickListener,
    private val isFavoritesScreen: Boolean = false // Flag to differentiate screens
) : ListAdapter<ImageResponse, ImageViewHolder>(ImageDiffCallback()) {

    private lateinit var imageChatItemBinding: ImageChildItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        imageChatItemBinding =
            ImageChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(imageChatItemBinding, listener, parent.context, isFavoritesScreen)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ImageDiffCallback : DiffUtil.ItemCallback<ImageResponse>() {
    override fun areItemsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
        return oldItem == newItem
    }

}
