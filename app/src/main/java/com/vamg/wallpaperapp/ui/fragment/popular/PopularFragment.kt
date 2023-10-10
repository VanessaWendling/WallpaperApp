package com.vamg.wallpaperapp.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.databinding.FragmentPopularBinding
import com.vamg.wallpaperapp.ui.fragment.adapter.photoadapter.PhotoAdapter
import com.vamg.wallpaperapp.ui.fragment.main.MainFragmentDirections
import com.vamg.wallpaperapp.ui.fragment.popular.viewmodel.PopularViewModel
import com.vamg.wallpaperapp.util.animationCancel
import com.vamg.wallpaperapp.util.pulseAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeLoadStage()
        observerFavoriteUiState()
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularWallpapers().collect { pagingData ->
                    photoAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun observeLoadStage() {
        lifecycleScope.launch {
            photoAdapter.loadStateFlow.collectLatest { loadState ->
                binding.imagePulseAnimation.isVisible =
                    loadState.source.refresh is LoadState.Loading
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.imagePulseAnimation.pulseAnimation()
                    }

                    is LoadState.NotLoading -> {
                        binding.imagePulseAnimation.animationCancel()
                    }

                    is LoadState.Error -> {
                        Toast.makeText(requireContext(), "Try again later", Toast.LENGTH_SHORT)
                            .show()
                        binding.imagePulseAnimation.animationCancel()
                    }
                }
            }
        }
    }


    private fun insertPhoto(photo: PhotoDomain) {
        viewModel.favoritePhoto(photo)
    }

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(::detail, ::insertPhoto)
        val gridLayoutManager = GridLayoutManager(context, 3)
        with(binding.recyclerView) {
            scrollToPosition(0)
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = photoAdapter
        }
    }

    private fun detail(photo: PhotoDomain) {
        val data = arrayOf(photo.srcDomain?.original, photo.avgColor)
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDownloadFragment(
                data
            )
        )
    }

    private fun observerFavoriteUiState() {
        viewModel.favoriteUiState.observe(viewLifecycleOwner) { favoriteUiState ->
            when (favoriteUiState) {
                PopularViewModel.FavoriteUiState.Loading -> 1
                is PopularViewModel.FavoriteUiState.FavoriteIcon -> {
                    favoriteUiState.icon
                }

                is PopularViewModel.FavoriteUiState.FavoritePhoto -> {
                    if (favoriteUiState.saved) favoriteItemMessage("item salvo")
                    else favoriteItemMessage("erro ao salvar")
                }
            }
        }
    }

    fun favoriteItemMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(ANIMATION_MODE_SLIDE).show()
    }


}