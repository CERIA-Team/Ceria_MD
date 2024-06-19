package com.ceria.capstone.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentLoginBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.utils.gone
import com.ceria.capstone.utils.invisible
import com.ceria.capstone.utils.toastLong
import com.ceria.capstone.utils.visible
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    private val loginActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data
            val response = AuthorizationClient.getResponse(result.resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.CODE -> {
                    Timber.d(response.code)
                    viewModel.login(response.code, "ceriaauthresponse://callback")
                }

                AuthorizationResponse.Type.ERROR -> {
                    showButtonLoading(false)
                    Timber.e("Auth error: " + response.error)
                }

                else -> {
                    showButtonLoading(false)
                    Timber.e("Unknown authorization response type : ${response.type}")
                }
            }
        }

    override fun setupUI() {
        checkSpotifyClient()
    }

    override fun onResume() {
        super.onResume()
        checkSpotifyClient()
    }

    override fun initData() {
        viewModel.checkToken()
    }

    override fun setupListeners() {
        with(binding) {
            tvInstallSpotify.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music")
                    )
                )
            }
            btnLogin.setOnClickListener {
                val request = AuthorizationRequest.Builder(
                    BuildConfig.SPOTIFY_CLIENT_ID,
                    AuthorizationResponse.Type.CODE,
                    "ceriaauthresponse://callback"
                ).setScopes(
                    arrayOf(
                        "streaming",
                        "app-remote-control",
                        "user-read-email",
                        "user-read-recently-played",
                        "user-read-currently-playing",
                        "user-read-playback-state"
                    )
                ).build()
                val intent =
                    AuthorizationClient.createLoginActivityIntent(requireActivity(), request)
                loginActivityResultLauncher.launch(intent)
                showButtonLoading(true)
            }
        }
    }

    override fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {
                    showButtonLoading(false)
                }

                is Result.Error -> {
                    requireContext().toastLong(it.error)
                    showButtonLoading(false)
                }

                Result.Loading -> {
                    showButtonLoading(true)
                }

                is Result.Success -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }
        }
        with(binding) {
            viewModel.tokenResponse.observe(viewLifecycleOwner) {
                when (it) {
                    Result.Empty, is Result.Error -> {
                        loginLayout.visible()
                        loadingLayout.gone()
                    }

                    Result.Loading -> {
                        loginLayout.gone()
                        loadingLayout.visible()
                    }

                    is Result.Success -> {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                }
            }
        }
    }

    override fun handleIntent() {
        val intent = requireActivity().intent
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith("ceriaauthresponse://callback")) {
            val response = AuthorizationResponse.fromUri(uri)
            response?.let { handleAuthorizationResponse(it) }
        } else {
            showButtonLoading(false)
        }
    }

    private fun handleAuthorizationResponse(response: AuthorizationResponse) {
        when (response.type) {
            AuthorizationResponse.Type.CODE -> {
                Timber.d("Authorization code: ${response.code}")
                viewModel.login(
                    response.code, "ceriaauthresponse://callback"
                )
            }

            AuthorizationResponse.Type.ERROR -> {
                showButtonLoading(false)
                Timber.e("Authorization error: ${response.error}")
            }

            else -> {
                showButtonLoading(false)
                Timber.e("Unknown authorization response type : ${response.type}")
            }
        }
    }

    private fun checkSpotifyClient() {
        with(binding) {
            val isSpotifyInstalled = SpotifyAppRemote.isSpotifyInstalled(requireContext())
            if (isSpotifyInstalled) {
                layoutSpotifyNotFound.invisible()
            } else {
                layoutSpotifyNotFound.visible()
            }
        }
    }

    private fun showButtonLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                btnLogin.invisible()
                pbLogin.visible()
            } else {
                pbLogin.invisible()
                btnLogin.visible()
            }
        }
    }

}