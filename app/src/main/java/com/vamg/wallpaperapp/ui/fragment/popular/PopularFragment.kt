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
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.databinding.FragmentPopularBinding
import com.vamg.wallpaperapp.ui.fragment.adapter.photoadapter.PhotoAdapter
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

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(::detail)
        val gridLayoutManager = GridLayoutManager(context, 3)
        with(binding.recyclerView) {
            scrollToPosition(0)
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = photoAdapter
        }
    }

    private fun detail(photo: PhotoDomain) {
        Toast.makeText(requireContext(), "Clicked! ;)", Toast.LENGTH_SHORT)
            .show()
    }
}