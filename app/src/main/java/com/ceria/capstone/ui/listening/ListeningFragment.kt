package com.ceria.capstone.ui.listening

import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentListeningBinding
import com.ceria.capstone.ui.common.BaseFragment

class ListeningFragment : BaseFragment<FragmentListeningBinding>(FragmentListeningBinding::inflate) {

    override fun setupUI() {

    }

    override fun setupListeners() {
        binding.stopsession.setOnClickListener {
            findNavController().navigate(R.id.summaryFragment)
        }
    }

    override fun setupObservers() {

    }
}