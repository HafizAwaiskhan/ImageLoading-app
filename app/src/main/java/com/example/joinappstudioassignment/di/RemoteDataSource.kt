package com.example.joinappstudioassignment.di

import com.example.joinappstudioassignment.data.implementations.datasourceimpl.ImageRemoteDataSourceImpl
import com.example.joinappstudioassignment.data.remotedata.ApiService
import com.example.joinappstudioassignment.domain.datasource.ImageRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideImageRemoteDataSource(apiService: ApiService): ImageRemoteDS {
        return ImageRemoteDataSourceImpl(apiService)
    }
}