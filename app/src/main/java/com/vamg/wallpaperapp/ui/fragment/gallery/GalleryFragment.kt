package com.vamg.wallpaperapp.ui.fragment.gallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.R
import com.vamg.wallpaperapp.databinding.FragmentCategoryBinding
import com.vamg.wallpaperapp.databinding.FragmentGalleryBinding
import com.vamg.wallpaperapp.framework.workmanager.WallpaperWork
import com.vamg.wallpaperapp.ui.fragment.adapter.galleryadapter.GalleryAdapter
import com.vamg.wallpaperapp.ui.fragment.gallery.viewmodel.GalleryViewModel
import com.vamg.wallpaperapp.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "WallpaperWorkerApp"

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModels()

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        backButton()
        getAll()
        startWork(workManager)
    }

    private fun getAll() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is GalleryViewModel.UiState.ShowGallery -> {
                    galleryAdapter.submitList(uiState.favorites)
                }

                is GalleryViewModel.UiState.EmptyGallery -> {
                    galleryAdapter.submitList(emptyList())
                }

                is GalleryViewModel.UiState.Error -> {
                    snackbarMessage("Error. Tente mais tarde :S")
                }
            }
        }
    }

    private fun initAdapter() {
        galleryAdapter = GalleryAdapter(::detail, ::delete)
        val gridLayoutManager = GridLayoutManager(context, 3)
        with(binding.galleryRv) {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = galleryAdapter
        }
    }

    private fun detail(photoDomain: PhotoDomain) {
        val data = arrayOf(photoDomain.srcDomain?.original, photoDomain.avgColor)
        findNavController().navigate(
            GalleryFragmentDirections.actionGalleryFragmentToDownloadFragment(
                data
            )
        )
    }

    private fun delete(photoDomain: PhotoDomain) {
        val dialog = CustomDialog(photoDomain) {
            viewModel.deleteFavorite(photoDomain)
        }
        dialog.show(childFragmentManager, "DELETE_PHOTO")
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
    }

    private fun startWork(workManager: WorkManager) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val wallpaperWorker =
            PeriodicWorkRequest.Builder(WallpaperWork::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            wallpaperWorker
        )
    }

    private fun cancel(workManager: WorkManager) {
        workManager.cancelUniqueWork(WORK_NAME)
        Log.i("WORKTEST", "CANCEL")
    }

}