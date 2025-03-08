package com.example.joinappstudioassignment.di

import com.example.joinappstudioassignment.data.remotedata.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    internal fun provideAppService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}