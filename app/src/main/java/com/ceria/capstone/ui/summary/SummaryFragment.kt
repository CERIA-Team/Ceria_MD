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

    override fun initData() {
        viewModel.getSessionDetail(args.listenSessionId)
    }

    override fun setupUI() {
        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
        songAdapter = SongAdapter(::addSongToFavorite, ::removeSongFromFavorite)
        binding.rvSongs.adapter = songAdapter
        binding.lastbpm.text = args.avgBpm
        binding.minutes.text = args.durationString
    }

    override fun setupListeners() {}

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
                    songAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun addSongToFavorite(song: SongDTO) {
        viewModel.addSongToFavorite(song)
    }

    private fun removeSongFromFavorite(song: SongDTO) {
        viewModel.removeSongFromFavorite(song)
    }
}
