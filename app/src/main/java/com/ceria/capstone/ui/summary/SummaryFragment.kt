package com.ceria.capstone.ui.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.databinding.FragmentSummaryBinding
import com.ceria.capstone.domain.adapter.SummaryAdapter
import com.ceria.capstone.ui.common.BaseFragment

class SummaryFragment : BaseFragment<FragmentSummaryBinding>(FragmentSummaryBinding::inflate) {
    private val viewModel: SummaryViewModel by viewModels()
    private lateinit var sessionId: String
    private lateinit var summaryAdapter: SummaryAdapter
    private var sessionDurationMinutes: Long = 0

    override fun setupUI() {
        binding.rvStopsession.layoutManager = LinearLayoutManager(requireContext())
        summaryAdapter = SummaryAdapter(emptyList())
        binding.rvStopsession.adapter = summaryAdapter

        // Get sessionId and sessionDuration from arguments
        arguments?.let {
            sessionId = it.getString("SESSION_ID", "") ?: ""
            sessionDurationMinutes = it.getLong("SESSION_DURATION_MINUTES", 0)
        }
    }

    override fun setupListeners() {
        // Setup listeners if needed
    }

    override fun setupObservers() {
        // Display session duration in minutes
        binding.duration.text = extractFirstDigit(sessionDurationMinutes)

        // Observe LiveData from ViewModel
        viewModel.getSummaryBySessionId(sessionId).observe(viewLifecycleOwner, Observer { summaryEntities ->
            summaryEntities?.let { entities ->
                // Filter out entities with null albumNames and artists
                val filteredEntities = entities.filter { entity ->
                    !entity.albumNames.isNullOrBlank() && !entity.artists.isNullOrBlank()
                }

                // Remove duplicates based on albumNames and artists
                val uniqueSummaries = filteredEntities.distinctBy { entity ->
                    entity.albumNames + entity.artists
                }

                val totalCount = uniqueSummaries.size
                Log.d("SummaryFragment", "Total count of valid unique entities: $totalCount")
                binding.songsplayed.text = totalCount.toString()

                summaryAdapter.setSummaryEntities(uniqueSummaries)
                Log.d("SummaryFragment", "Received summaryEntities: $uniqueSummaries")
            }
        })
    }

    private fun extractFirstDigit(duration: Long): String {
        val durationString = duration.toString()
        return if (durationString.isNotEmpty()) {
            durationString.substring(0, 1)
        } else {
            "0"
        }
    }
    companion object {
        fun newInstance(sessionId: String, sessionDurationMinutes: Long): SummaryFragment {
            val fragment = SummaryFragment()
            val args = Bundle()
            args.putString("SESSION_ID", sessionId)
            args.putLong("SESSION_DURATION_MINUTES", sessionDurationMinutes)
            fragment.arguments = args
            return fragment
        }
    }
}
