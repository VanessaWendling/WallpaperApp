package com.vamg.wallpaperapp.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vamg.wallpaperapp.R
import com.vamg.wallpaperapp.databinding.FragmentMainBinding
import com.vamg.wallpaperapp.framework.local.Carousel
import com.vamg.wallpaperapp.ui.fragment.category.CategoryFragment
import com.vamg.wallpaperapp.ui.fragment.collections.CollectionsFragment
import com.vamg.wallpaperapp.ui.fragment.popular.PopularFragment
import com.vamg.wallpaperapp.ui.pageadapter.ViewPagerAdapter

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val tabTitles = listOf("Popular", "Collections", "Categories")
    private val fragments = listOf(PopularFragment(), CollectionsFragment(), CategoryFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //a ordem de inicialização importa
        initToolbar()
        initViewPager()
        initTabLayout()
        initCarousel()
        detail()
    }

    private fun initCarousel(){
        with(binding.carouselViewFlipper){
            setOutAnimation(activity, android.R.anim.slide_out_right)
            setup(Carousel.list)
            setLayout()
        }
    }

    private fun initToolbar(){
        binding.toolbar.title = "Wallpapers"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun initViewPager(){
        val pagerAdapter = ViewPagerAdapter(requireActivity(), fragments)
        binding.run {
            viewPager.adapter = pagerAdapter
        }
    }

    private fun initTabLayout(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun detail() {
        //captura qualquer açãod e click em alguma view
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_galleryFragment)
        }
    }


}