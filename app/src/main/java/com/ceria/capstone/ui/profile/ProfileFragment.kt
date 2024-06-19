package com.ceria.capstone.ui.profile

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentProfileBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.ui.liked.LikedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun initData() {
        viewModel.getProfile()
    }

    override fun setupUI() {
        // You can perform UI setup if needed
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
        viewModel.profileResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    with(binding) {
                        tvDisplayName.text = result.data.displayName
                        tvEmailAddress.text = result.data.email
                        Glide.with(requireContext()).load(
                            result.data.imageUrl.toString()
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
        viewModel.favoriteCount.observe(viewLifecycleOwner) { count ->
            Log.d("ProfileFragment", "Favorite Count: $count")
            binding.textView3.text = resources.getQuantityString(R.plurals.favorite_count_plural, count, count)
        }

        viewModel.getFavoriteCount()
    }

}
