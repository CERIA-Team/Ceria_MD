package com.ceria.capstone.ui.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.databinding.FragmentSettingBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val viewModel: SettingViewModel by viewModels()
    override fun setupUI() {

    }

    override fun setupListeners() {
        with(binding) {
            tvLogout.setOnClickListener {
                viewModel.logout()
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment())
            }
        }
    }

    override fun setupObservers() {

    }

}