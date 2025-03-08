package com.example.joinappstudioassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.joinappstudioassignment.common.BaseFragment
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.databinding.FragmentFavoriteBinding
import com.example.joinappstudioassignment.ui.adapters.ImageAdapter
import com.example.joinappstudioassignment.utils.ItemClickListener
import com.example.joinappstudioassignment.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {

    private val favoriteViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter(isFavoritesScreen = true, listener = object : ItemClickListener {
            override fun onRemoveFromFavorites(image: ImageResponse) {
                removeFromFavorites(image)
            }
        })

        binding.favoriteImagesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = imageAdapter
        }
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            favoriteViewModel.favoriteImages.collect { favoriteList ->
                imageAdapter.submitList(favoriteList)
            }
        }
    }

    private fun removeFromFavorites(image: ImageResponse) {
        favoriteViewModel.removeImage(image)

    }
}