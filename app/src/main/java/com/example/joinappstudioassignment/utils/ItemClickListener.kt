package com.example.joinappstudioassignment.utils

import com.example.joinappstudioassignment.data.responsemodel.ImageResponse

interface ItemClickListener {
    fun onAddToFavorites(image: ImageResponse) {}  // Default empty implementation
    fun onRemoveFromFavorites(image: ImageResponse) {}  // Default empty implementation
}