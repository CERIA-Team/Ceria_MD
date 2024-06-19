package com.ceria.capstone.ui.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentDetailBinding
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.ui.common.SongAdapter
import com.ceria.capstone.utils.gone
import com.ceria.capstone.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var songsAdapter: SongAdapter
    override fun initData() {
        viewModel.getSessionDetail(args.listenSessionId)
    }

    override fun setupUI() {
        songsAdapter = SongAdapter(::addSongToFavorite, ::removeSongFromFavorite)
        with(binding) {
            rvSongs.adapter = songsAdapter
            rvSongs.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun setupListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setupObservers() {
        with(binding) {
            viewModel.songs.observe(viewLifecycleOwner) {
                when (it) {
                    Result.Empty -> {}
                    is Result.Error -> {}
                    Result.Loading -> {
                        rvSongs.gone()
                        loadingSongs.visible()
                    }

                    is Result.Success -> {
                        songsAdapter.submitList(it.data)
                        songsplayed.text = it.data.size.toString()
                        loadingSongs.gone()
                        rvSongs.visible()
                    }
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