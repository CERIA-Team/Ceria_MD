package com.ceria.capstone.ui.home

import androidx.fragment.app.viewModels
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentHomeBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    override fun initData() {
        viewModel.getProfile()
    }

    override fun setupUI() {
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    binding.tvHelloUser.text = getString(R.string.hello_user, it.data.displayName)
                }
            }
        }
    }

}