package com.ceria.capstone.ui.login

import com.ceria.capstone.databinding.FragmentLoginBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun setupUI() {
    }

    override fun setupListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                //TODO("Panggil Spotify sesuai ProtoActivity")
            }
        }
    }


}