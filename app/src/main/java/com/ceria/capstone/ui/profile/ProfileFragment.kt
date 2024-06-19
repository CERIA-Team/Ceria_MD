package com.ceria.capstone.ui.profile

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentProfileBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    override fun initData() {
        viewModel.getProfile()
        viewModel.getFavoriteSongs()
    }

    override fun setupUI() {

    }

    override fun setupListeners() {
        with(binding) {
            cardLiked.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_likedFragment)
            }
            ibSettings.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
            }
        }
    }


    override fun setupObservers() {
        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    with(binding) {
                        tvDisplayName.text = it.data.displayName
                        tvEmailAddress.text = it.data.email
                        Glide.with(requireContext()).load(
                            it.data.imageUrl.toString()
                        ).placeholder(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.sample_profile
                            )
                        ).error(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.sample_profile
                            )
                        ).into(ivProfile)
                    }
                }
            }
        }
        viewModel.countFavorite.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    binding.tvLikedCount.text = getString(R.string.n_songs, it.data.size)
                }
            }
        }
    }

}