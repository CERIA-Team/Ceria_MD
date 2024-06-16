package com.ceria.capstone.ui.monitor

import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentMonitorBinding
import com.ceria.capstone.ui.common.BaseFragment

class MonitorFragment : BaseFragment<FragmentMonitorBinding>(FragmentMonitorBinding::inflate) {
    override fun setupUI() {
    }

    override fun setupListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                findNavController().navigate(R.id.action_monitorFragment_to_listeningFragment)
            }
        }
    }

    override fun setupObservers() {
    }


}