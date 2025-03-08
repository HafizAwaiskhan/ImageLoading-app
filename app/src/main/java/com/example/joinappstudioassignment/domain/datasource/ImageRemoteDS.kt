package com.example.joinappstudioassignment.domain.datasource

import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import retrofit2.Response

interface ImageRemoteDS {
    suspend fun getImagesData(page: Int): Response<List<ImageResponse>>
}