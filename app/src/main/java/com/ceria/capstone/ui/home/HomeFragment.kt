package com.ceria.capstone.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentHomeBinding
import com.ceria.capstone.ui.MainActivity
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.ui.home.adapter.SessionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sessionAdapter: SessionAdapter

    override fun initData() {
        viewModel.getProfile()
    }

    override fun setupUI() {
        with(binding) {
            sessionAdapter = SessionAdapter()
            rvHistorySession.layoutManager = LinearLayoutManager(requireContext())
            rvHistorySession.adapter = sessionAdapter
            sessionAdapter.submitList(listOf("A", "B", "C", "D", "E", "F"))
        }
    }

    override fun setupListeners() {
        with(binding) {
            button.setOnClickListener {
                val mainActivity = requireActivity() as MainActivity
                mainActivity.binding.fabPlay.performClick()
            }
        }
    }

    override fun setupObservers() {
        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    binding.tvHelloUser.text = getString(R.string.hello_user, it.data.displayName)
                }
            }
        }
    }

}