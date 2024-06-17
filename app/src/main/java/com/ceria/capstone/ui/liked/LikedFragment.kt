package com.ceria.capstone.ui.liked

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentLikedBinding
import com.ceria.capstone.domain.adapter.LikedAdapter
import com.ceria.capstone.ui.common.BaseFragment

class LikedFragment : BaseFragment<FragmentLikedBinding>(FragmentLikedBinding::inflate) {
    private val likedViewModel: LikedViewModel by viewModels()

    override fun setupUI() {
        binding.rvLiked.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupListeners() {
        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }
    }

    override fun setupObservers() {
        likedViewModel.getfavoriteuser()?.observe(viewLifecycleOwner, Observer { favoriteList ->
            favoriteList?.let {
                val adapter = LikedAdapter(it, likedViewModel) { favoriteEntity ->
                    likedViewModel.removeFavorite(favoriteEntity)
                }
                binding.rvLiked.adapter = adapter
            }
        })
    }
}
