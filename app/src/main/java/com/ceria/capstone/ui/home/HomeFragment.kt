package com.ceria.capstone.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentHomeBinding
import com.ceria.capstone.ui.MainActivity
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.utils.gone
import com.ceria.capstone.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sessionAdapter: SessionAdapter

    override fun initData() {
        viewModel.getProfile()
        viewModel.getSessions()
    }

    override fun setupUI() {
        with(binding) {
            sessionAdapter = SessionAdapter(::viewDetail)
            rvHistorySession.layoutManager = LinearLayoutManager(requireContext())
            rvHistorySession.adapter = sessionAdapter
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
        viewModel.sessions.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {
                    binding.rvHistorySession.gone()
                    binding.loadingSession.visible()
                }
                is Result.Success -> {
                    binding.loadingSession.gone()
                    binding.rvHistorySession.visible()
                    sessionAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun viewDetail(id: String) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }
}