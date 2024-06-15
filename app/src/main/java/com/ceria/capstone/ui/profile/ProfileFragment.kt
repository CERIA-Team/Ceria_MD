package com.ceria.capstone.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentProfileBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    override fun setupUI() {
        viewModel.getProfile()
    }

    override fun setupListeners() {
        with(binding) {
            cardLiked.setOnClickListener {
                findNavController().navigate(R.id.likedFragment)
            }
            ibSettings.setOnClickListener {
                findNavController().navigate(R.id.settingFragment)
            }
        }
    }

    override fun setupObservers() {
    }

}