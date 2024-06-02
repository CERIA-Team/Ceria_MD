package com.ceria.capstone.ui.login

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentLoginBinding
import com.ceria.capstone.ui.common.BaseFragment
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
                    viewModel.getAccessToken(response.code, "ceriaauthresponse://callback")
                }

                AuthorizationResponse.Type.ERROR -> {
                    Timber.e("Auth error: " + response.error)
                }

                else -> {
                    Timber.e("Unknown authorization response type : ${response.type}")
                }
            }
        }

    override fun setupUI() {
    }

    override fun setupListeners() {
        with(binding) {
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
                        "user-read-recently-played"
                    )
                ).build()
                val intent =
                    AuthorizationClient.createLoginActivityIntent(requireActivity(), request)
                loginActivityResultLauncher.launch(intent)
            }
        }
    }

    override fun setupObservers() {
        viewModel.tokenResponse.observe(viewLifecycleOwner) {
            when (it) {
                Result.Empty -> {}
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    //TODO("SIMPAN TOKEN DI SHAREDPREF ATAU BACKEND?")
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
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
        }
    }

    private fun handleAuthorizationResponse(response: AuthorizationResponse) {
        when (response.type) {
            AuthorizationResponse.Type.CODE -> {
                Timber.d("Authorization code: ${response.code}")
                viewModel.getAccessToken(
                    response.code, "ceriaauthresponse://callback"
                )
            }

            AuthorizationResponse.Type.ERROR -> {
                Timber.e("Authorization error: ${response.error}")
            }

            else -> {
                Timber.e("Unknown authorization response type : ${response.type}")
            }
        }
    }


}