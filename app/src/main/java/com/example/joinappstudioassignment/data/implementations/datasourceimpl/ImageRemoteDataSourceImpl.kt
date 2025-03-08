package com.example.joinappstudioassignment.data.implementations.datasourceimpl

import com.example.joinappstudioassignment.data.remotedata.ApiService
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.domain.datasource.ImageRemoteDS
import retrofit2.Response

class ImageRemoteDataSourceImpl(private val apiService: ApiService): ImageRemoteDS{
    override suspend fun getImagesData(page: Int): Response<List<ImageResponse>> {
        return apiService.getImages(page,30)
    }
}