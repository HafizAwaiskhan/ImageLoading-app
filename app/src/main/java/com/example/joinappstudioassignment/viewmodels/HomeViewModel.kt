package com.example.joinappstudioassignment.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.joinappstudioassignment.MyApplication
import com.example.joinappstudioassignment.R
import com.example.joinappstudioassignment.data.remotedata.Resource
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.domain.repository.ImageRepository
import com.example.joinappstudioassignment.utils.INTERNET_UNAVAILABLE_CODE
import com.example.joinappstudioassignment.utils.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val getImagesFromApiResponse = MutableLiveData<Resource<List<ImageResponse>>>()

    private val _allImages = MutableLiveData<List<ImageResponse>>(emptyList())
    val allImages: LiveData<List<ImageResponse>> get() = _allImages

    private var currentPage = 1
    private var isLoading = false

    init {
        getImages(currentPage)  // Load first page initially
    }

    private fun getImages(page: Int) = viewModelScope.launch(dispatcher) {
        if (isLoading) return@launch  // Prevent duplicate API calls
        isLoading = true

        if (page == 1) {
            getImagesFromApiResponse.postValue(Resource.Loading())
        }

        try {
            if (NetworkUtil.hasInternetConnection(MyApplication.appContext)){
                Log.d("*****", "page For Api : $currentPage")
                val apiResponse = repository.getImages(page)
                if (apiResponse is Resource.Success) {
                    val newImages = apiResponse.data ?: emptyList()
                    _allImages.postValue(_allImages.value.orEmpty() + newImages) // Append new images
                }
                getImagesFromApiResponse.postValue(apiResponse)
            } else {
                getImagesFromApiResponse.postValue(
                    Resource.Error(MyApplication.appContext.getString(R.string.internet_is_unavailable), INTERNET_UNAVAILABLE_CODE, MyApplication.appContext.getString(
                        R.string.internet_is_unavailable)))
            }
        } catch (e: Exception) {
            getImagesFromApiResponse.postValue(
                Resource.Error(
                    e.toString() , e.cause.hashCode(),e.message.toString()))
        }
        isLoading = false
    }

    fun saveImage(image: ImageResponse, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.saveToFavorites(image)
            onResult(success)
        }
    }

    val favoriteImages: StateFlow<List<ImageResponse>> = repository.getFavoriteImages()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeImage(image: ImageResponse) = viewModelScope.launch {
        repository.removeFromFavorites(image)
    }

    fun loadNextPage() {
        if (!isLoading) {
            currentPage++
            Log.d("*****", "pageSize: $currentPage")
            getImages(currentPage)
        }
    }
}