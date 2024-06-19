package com.ceria.capstone.ui.monitor

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentMonitorBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.utils.invisible
import com.ceria.capstone.utils.toastLong
import com.ceria.capstone.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonitorFragment : BaseFragment<FragmentMonitorBinding>(FragmentMonitorBinding::inflate) {
    private val viewModel: MonitorViewModel by viewModels()
    override fun setupUI() {
    }

    override fun setupListeners() {
        with(binding) {
            btnStart.setOnClickListener {
                viewModel.startSession()
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
        viewModel.sessionResponse.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    Result.Empty -> {}
                    is Result.Error -> {
                        loadingStartSession.invisible()
                        btnStart.visible()
                        requireActivity().toastLong(getString(R.string.error_when_starting_session))
                    }

                    Result.Loading -> {
                        btnStart.invisible()
                        loadingStartSession.visible()
                    }

                    is Result.Success -> {
                        findNavController().navigate(
                            MonitorFragmentDirections.actionMonitorFragmentToListeningFragment(
                                viewModel.initialHeartRate.value ?: 70, it.data
                            )
                        )
                    }
                }
            }
        }
    }


}