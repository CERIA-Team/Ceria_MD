package com.ceria.capstone.ui.liked

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentLikedBinding
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.ui.common.SongAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedFragment : BaseFragment<FragmentLikedBinding>(FragmentLikedBinding::inflate) {
    private val viewModel: LikedViewModel by viewModels()
    private lateinit var songAdapter: SongAdapter

    override fun initData() {
        viewModel.getFavoriteSongs()
    }

    override fun setupUI() {
        songAdapter = SongAdapter(::addSongToFavorite, ::removeSongFromFavorite)
        with(binding) {
            rvLiked.layoutManager = LinearLayoutManager(requireContext())
            rvLiked.adapter = songAdapter
        }
    }

    override fun setupListeners() {
        binding.setting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                songAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun setupObservers() {
        viewModel.songs.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    songAdapter.updateList(it.data)
                }
            }
        }

    }

    private fun addSongToFavorite(song: SongDTO) {
        viewModel.addSongToFavorite(song)
        viewModel.getFavoriteSongs()
    }

    private fun removeSongFromFavorite(song: SongDTO) {
        viewModel.removeSongFromFavorite(song)
        viewModel.getFavoriteSongs()
    }
}
