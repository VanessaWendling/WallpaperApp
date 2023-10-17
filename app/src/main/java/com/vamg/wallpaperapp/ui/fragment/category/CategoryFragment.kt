package com.vamg.wallpaperapp.ui.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vamg.wallpaperapp.databinding.FragmentCategoryBinding
import com.vamg.wallpaperapp.databinding.FragmentPopularBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}