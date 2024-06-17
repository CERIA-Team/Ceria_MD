package com.ceria.capstone.ui.addsong

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentAddSongBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AddSongFragment : BaseFragment<FragmentAddSongBinding>(FragmentAddSongBinding::inflate) {
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentTrack: Track? = null

    override fun setupUI() {
        with(binding) {
            val connectionParams = ConnectionParams.Builder(BuildConfig.SPOTIFY_CLIENT_ID)
                .setRedirectUri("ceriaauthresponse://callback").showAuthView(false).build()

            SpotifyAppRemote.connect(
                requireContext(),
                connectionParams,
                object : Connector.ConnectionListener {
                    override fun onConnected(appRemote: SpotifyAppRemote) {
                        spotifyAppRemote = appRemote
                        Timber.d("Connected! Yay!")
                        fetchSongs()
                    }

                    override fun onFailure(throwable: Throwable) {
                        Timber.e("Failed to connect: " + throwable.message, throwable)
                    }
                })
        }
    }

    private fun fetchSongs() {
        val playlistURI = "spotify:playlist:37i9dQZF1DX4JAvHpjipBk"
        spotifyAppRemote?.playerApi?.play(playlistURI)
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            currentTrack = it.track
            currentTrack?.let { track ->
                binding.artist.text = track.name
                binding.artist.isSelected = true
                binding.album.text = track.artists.joinToString(separator = ", ") { artist -> artist.name }
                binding.album.isSelected = true
                Glide.with(requireContext()).load(
                    track.imageUri.raw?.replace(
                        "spotify:image:", "https://i.scdn.co/image/"
                    )
                ).into(binding.image)
            }
        }
    }

    override fun setupListeners() {
        // Implement any listeners if needed
    }

    override fun setupObservers() {
        // Implement any observers if needed
    }
}
