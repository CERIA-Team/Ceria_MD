package com.ceria.capstone.ui.summary

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentSummaryBinding
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.ui.common.SongAdapter
import com.ceria.capstone.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SummaryFragment : BaseFragment<FragmentSummaryBinding>(FragmentSummaryBinding::inflate) {
    private val viewModel: SummaryViewModel by viewModels()
    private val args: SummaryFragmentArgs by navArgs()
    private lateinit var songAdapter: SongAdapter
//    private var sessionDurationMinutes: Long = 0

    override fun initData() {
        viewModel.getSessionDetail(args.listenSessionId)
    }

    override fun setupUI() {
        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
        songAdapter = SongAdapter(::addSongToFavorite, ::removeSongFromFavorite)
        binding.rvSongs.adapter = songAdapter
//=======
//>>>>>>> Stashed changes
//        songAdapter = SongAdapter(emptyList(),viewModel)
//        binding.rvStopsession.adapter = songAdapter

        // Get sessionId and sessionDuration from arguments
//        arguments?.let {
//            sessionId = it.getString("SESSION_ID", "") ?: ""
//            sessionDurationMinutes = it.getLong("SESSION_DURATION_MINUTES", 0)
//        }
//>>>>>>> d496628957a59956f658224fba5756676fb00558
    }

    override fun setupListeners() {}

    override fun setupObservers() {
//<<<<<<< Updated upstream
//=======
//<<<<<<< HEAD
        viewModel.songs.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {
                    requireActivity().toastLong("Empty")
//=======
//>>>>>>> Stashed changes
                    // Display session duration in minutes
//        binding.duration.text = extractFirstDigit(sessionDurationMinutes)

                    // Observe LiveData from ViewModel
//        viewModel.getSummaryBySessionId(sessionId).observe(viewLifecycleOwner, Observer { summaryEntities ->
//            summaryEntities?.let { entities ->
//                // Filter out entities with null albumNames and artists
//                val filteredEntities = entities.filter { entity ->
//                    !entity.albumNames.isNullOrBlank() && !entity.artists.isNullOrBlank()
//>>>>>>> d496628957a59956f658224fba5756676fb00558
                }

                is Result.Error -> {
                    requireActivity().toastLong(it.error)
                }

//<<<<<<< HEAD
                Result.Loading -> {}
                is Result.Success -> {
                    binding.songsplayed.text = it.data.size.toString()
                    songAdapter.submitList(it.data)
                }
            }
//=======
//                val totalCount = uniqueSummaries.size
//                Log.d("SummaryFragment", "Total count of valid unique entities: $totalCount")
//                binding.songsplayed.text = totalCount.toString()
//
//                songAdapter.setSummaryEntities(uniqueSummaries)
//                Log.d("SummaryFragment", "Received summaryEntities: $uniqueSummaries")
//            }
//        })
//    }
//
//    private fun extractFirstDigit(duration: Long): String {
//        val durationString = duration.toString()
//        return if (durationString.isNotEmpty()) {
//            durationString.substring(0, 1)
//        } else {
//            "0"
//        }
//    }
//    companion object {
//        fun newInstance(sessionId: String, sessionDurationMinutes: Long): SummaryFragment {
//            val fragment = SummaryFragment()
//            val args = Bundle()
//            args.putString("SESSION_ID", sessionId)
//            args.putLong("SESSION_DURATION_MINUTES", sessionDurationMinutes)
//            fragment.arguments = args
//            return fragment
//>>>>>>> d496628957a59956f658224fba5756676fb00558
        }
    }

    private fun addSongToFavorite(song: SongDTO) {
        viewModel.addSongToFavorite(song)
    }

    private fun removeSongFromFavorite(song: SongDTO) {
        viewModel.removeSongFromFavorite(song)
    }
}
