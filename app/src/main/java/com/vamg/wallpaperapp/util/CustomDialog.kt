package com.vamg.wallpaperapp.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vamg.core.domain.model.PhotoDomain
import com.vamg.wallpaperapp.databinding.CustomDialogFragmentBinding
import com.vamg.wallpaperapp.extentions.loadBlurredImageWithPlaceholder

class CustomDialog(
    private val photo: PhotoDomain,
    private val clickListener: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = CustomDialogFragmentBinding.inflate(layoutInflater)
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            clickListener.invoke()
            dismiss()
        }
        binding.image.loadBlurredImageWithPlaceholder(photo.srcDomain?.small, photo.avgColor)

        return MaterialAlertDialogBuilder(
            requireContext(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        ).setCancelable(false)
            .setView(binding.root)
            .create()
            .apply { window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
    }
}