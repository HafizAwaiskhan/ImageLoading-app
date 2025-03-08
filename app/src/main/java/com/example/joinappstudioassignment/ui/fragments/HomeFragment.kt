package com.example.joinappstudioassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joinappstudioassignment.common.BaseFragment
import com.example.joinappstudioassignment.data.remotedata.Resource
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse
import com.example.joinappstudioassignment.databinding.FragmentHomeBinding
import com.example.joinappstudioassignment.ui.adapters.ImageAdapter
import com.example.joinappstudioassignment.utils.ItemClickListener
import com.example.joinappstudioassignment.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavorites()
        observeImagesData()
        setupPagination()
        binding.goToFavorites.setOnClickListener {
            navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment())
        }
    }

    private fun setupRecyclerView() {

        imageAdapter = ImageAdapter(isFavoritesScreen = false, listener = object : ItemClickListener {
            override fun onAddToFavorites(image: ImageResponse) {
                saveToFavorites(image)
            }
        })


        binding.imagesMainRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = imageAdapter
        }
    }

    private fun observeImagesData() {
        homeViewModel.allImages.observe(viewLifecycleOwner) { images ->
            imageAdapter.submitList(images)
        }

        homeViewModel.getImagesFromApiResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> showLoading(false)
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveToFavorites(image: ImageResponse) {
        // Save to favorites
        homeViewModel.saveImage(image) { success ->
            if (success) {
                binding.goToFavorites.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Added to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Already in favorites!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPagination() {
        binding.imagesMainRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // Load next page when the user scrolls close to the bottom
                if (lastVisibleItemPosition >= totalItemCount - 5) {
                    homeViewModel.loadNextPage()
                }
            }
        })
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            homeViewModel.favoriteImages.collect { favoriteList ->
                favoriteList.let {
                    binding.goToFavorites.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }
}