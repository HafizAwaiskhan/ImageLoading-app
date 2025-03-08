package com.example.joinappstudioassignment.di

import com.example.joinappstudioassignment.data.implementations.repositoryimpl.ImageRepositoryImpl
import com.example.joinappstudioassignment.data.localdata.dao.ImageDao
import com.example.joinappstudioassignment.domain.datasource.ImageRemoteDS
import com.example.joinappstudioassignment.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideImagesRepository(imagesRemoteDataSource: ImageRemoteDS, imagesDao: ImageDao): ImageRepository {
        return ImageRepositoryImpl(imagesRemoteDataSource, imagesDao)
    }
}