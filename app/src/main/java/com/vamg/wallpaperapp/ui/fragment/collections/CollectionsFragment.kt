package com.vamg.wallpaperapp.ui.fragment.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vamg.wallpaperapp.R
import com.vamg.wallpaperapp.databinding.FragmentCollectionsBinding
import com.vamg.wallpaperapp.databinding.FragmentPopularBinding

class CollectionsFragment : Fragment() {
    private lateinit var binding: FragmentCollectionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}