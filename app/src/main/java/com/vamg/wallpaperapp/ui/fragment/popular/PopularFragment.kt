package com.vamg.wallpaperapp.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vamg.wallpaperapp.databinding.FragmentPopularBinding

class PopularFragment : Fragment() {
    private lateinit var binding : FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }
}