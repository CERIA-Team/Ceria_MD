package com.ceria.capstone.ui.profile

import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentProfileBinding
import com.ceria.capstone.ui.common.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun setupUI() {
    }

    override fun setupListeners() {
        binding.CardViewLiked.setOnClickListener() {
            findNavController().navigate(R.id.likedFragment)
        }
        binding.setting.setOnClickListener() {
            findNavController().navigate(R.id.settingFragment)
        }
    }

    override fun setupObservers() {
    }

}