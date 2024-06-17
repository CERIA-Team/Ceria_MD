package com.ceria.capstone.ui.liked

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentLikedBinding
import com.ceria.capstone.domain.adapter.LikedAdapter
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController

class LikedFragment : Fragment(R.layout.fragment_liked) {

    private val likedViewModel: LikedViewModel by viewModels()
    private lateinit var binding: FragmentLikedBinding
    private lateinit var adapter: LikedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLikedBinding.bind(view)

        setupUI()
        setupListeners()
        setupObservers()
    }

    private fun setupUI() {
        binding.rvLiked.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }
        binding.addsong.setOnClickListener {
            findNavController().navigate(R.id.addsongFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupObservers() {
        likedViewModel.getfavoriteuser()?.observe(viewLifecycleOwner, Observer { favoriteList ->
            favoriteList?.let {
                adapter = LikedAdapter(it, likedViewModel) { favoriteEntity ->
                    likedViewModel.removeFavorite(favoriteEntity)
                }
                binding.rvLiked.adapter = adapter
            }
        })
    }
}
