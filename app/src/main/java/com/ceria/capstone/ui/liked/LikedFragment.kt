package com.ceria.capstone.ui.liked

import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentLikedBinding
import com.ceria.capstone.ui.common.BaseFragment

class LikedFragment : BaseFragment<FragmentLikedBinding>(FragmentLikedBinding::inflate) {

    override fun setupUI() {

    }

    override fun setupListeners() {
        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }
    }

    override fun setupObservers() {

    }
}