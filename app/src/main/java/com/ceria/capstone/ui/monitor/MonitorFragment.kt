package com.ceria.capstone.ui.monitor

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentMonitorBinding
import com.ceria.capstone.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonitorFragment : BaseFragment<FragmentMonitorBinding>(FragmentMonitorBinding::inflate) {
    private val viewModel: MonitorViewModel by viewModels()
    override fun setupUI() {
    }

    override fun setupListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                findNavController().navigate(
                    MonitorFragmentDirections.actionMonitorFragmentToListeningFragment(
                        viewModel.initialHeartRate.value!!
                    )
                )
            }
            ibIncrement.setOnClickListener {
                viewModel.incrementHeartRate()
            }
            ibDecrement.setOnClickListener {
                viewModel.decrementHeartRate()
            }
        }
    }

    override fun setupObservers() {
        viewModel.initialHeartRate.observe(viewLifecycleOwner) {
            binding.bpmMonitor.text = it.toString()
        }
    }


}