package com.ceria.capstone.ui.summary

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ceria.capstone.data.roomsummary.SummaryEntity
import com.ceria.capstone.databinding.FragmentSummaryBinding
import com.ceria.capstone.ui.common.BaseFragment

class SummaryFragment : BaseFragment<FragmentSummaryBinding>(FragmentSummaryBinding::inflate) {

    private val viewModel: SummaryViewModel by viewModels()
    private lateinit var sessionId: String

    override fun setupUI() {
        // Setup UI elements if needed
        arguments?.let {
            sessionId = it.getString("SESSION_ID", "")
        }
    }

    override fun setupListeners() {
        // Setup listeners if needed
    }

    override fun setupObservers() {
        // Observe LiveData from ViewModel
        viewModel.getSummaryBySessionId(sessionId).observe(viewLifecycleOwner, Observer { summaryEntity ->
            // Update UI with summaryEntity data
            binding.name.text = summaryEntity.sessionId
            binding.name2.text = summaryEntity.artists

            // Example of handling imageUrls (splitting and loading images)
            val imageUrlList = summaryEntity.imageUrls.split(",")
            // Load images based on imageUrlList

            // Handle other UI updates as needed
        })
    }

    companion object {
        fun newInstance(sessionId: String): SummaryFragment {
            val fragment = SummaryFragment()
            val args = Bundle()
            args.putString("SESSION_ID", sessionId)
            fragment.arguments = args
            return fragment
        }
    }
}
