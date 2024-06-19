package com.ceria.capstone.ui.summary

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentSummaryBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SummaryFragment : BaseFragment<FragmentSummaryBinding>(FragmentSummaryBinding::inflate) {
    private val viewModel: SummaryViewModel by viewModels()
    private val args: SummaryFragmentArgs by navArgs()
    private lateinit var summaryAdapter: SummaryAdapter

    override fun initData() {
        viewModel.getSessionDetail(args.listenSessionId)
    }

    override fun setupUI() {
        binding.rvStopsession.layoutManager = LinearLayoutManager(requireContext())

        summaryAdapter = SummaryAdapter(viewModel)
        binding.rvStopsession.adapter = summaryAdapter

        val lastBpm = arguments?.getString("lastBpm") ?: ""
        binding.lastbpm.text= lastBpm

        val duration = arguments?.getString("trackDuration") ?: ""
        binding.minutes.text= duration

    }

    override fun setupListeners() {
    }

    override fun setupObservers() {

        viewModel.songs.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {
                    requireActivity().toastLong("Empty")
                }

                is Result.Error -> {
                    requireActivity().toastLong(it.error)
                }
                Result.Loading -> {}
                is Result.Success -> {
                    binding.songsplayed.text = it.data.size.toString()
                    summaryAdapter.submitList(it.data)
                }
            }

        }
    }
}
