package com.ceria.capstone.ui.listening

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.R
import com.ceria.capstone.databinding.FragmentListeningBinding
import com.ceria.capstone.ui.common.BaseFragment
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.UUID

@AndroidEntryPoint
class ListeningFragment :
    BaseFragment<FragmentListeningBinding>(FragmentListeningBinding::inflate) {
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var playbackPositionTimer: Timer? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var isUserSeeking = false
    private val debounceHandler = Handler(Looper.getMainLooper())
    private var debounceRunnable: Runnable? = null
    private val viewModel: ListeningViewModel by viewModels()
    private val args: ListeningFragmentArgs by navArgs()
    private var previousTrack: Track? = null
    override fun initData() {
        viewModel.setCurrentHeartRate(args.initialBpm)
    }
//     private var currentTrack: Track? = null
//     var _isChecked = false
//     private var sessionId: String = ""

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
                        spotifyAppRemote?.let { remote ->
                            val dummyTrack = listOf(
                                "3sGJmCUfZKbbjtZ24eaepn",
                                "6Jp404HEXaNS4FD9ZAMub1",
                                "2K3areNeCsCI55wKupEhBW"
                            )
                            remote.playerApi.play("spotify:track:${dummyTrack[0]}")
                            remote.playerApi.queue("spotify:track:${dummyTrack[1]}")
                            remote.playerApi.queue("spotify:track:${dummyTrack[2]}")
                            remote.playerApi.subscribeToPlayerState().setEventCallback {

                                val currentTrack: Track = it.track
                                if (currentTrack.uri != previousTrack?.uri) {
                                    titlealbum.text = currentTrack.name
                                    titlealbum.isSelected = true
                                    groupalbum.text =
                                        currentTrack.artists.joinToString(separator = ", ") { artist -> artist.name }
                                    groupalbum.isSelected = true
                                    textbpm2.isSelected = true
                                    Glide.with(requireContext()).load(
                                        currentTrack.imageUri.raw?.replace(
                                            "spotify:image:", "https://i.scdn.co/image/"
                                        )
                                    ).placeholder(
                                        ContextCompat.getDrawable(
                                            requireContext(), R.drawable.placeholder_song
                                        )
                                    ).error(
                                        ContextCompat.getDrawable(
                                            requireContext(), R.drawable.placeholder_song
                                        )
                                    ).into(imagelistening)
                                    timeend.text = formatMilliseconds(currentTrack.duration)
                                    seekbar.max = (currentTrack.duration / 1000).toInt()
                                    Timber.d("${currentTrack.name} by ${currentTrack.artist.name}")
                                    previousTrack = currentTrack
                                }

//                                 currentTrack = it.track
//                                 updateFavoriteToggleState()

//                                 if (sessionId.isEmpty()) {
//                                     sessionId = generateSessionId(currentTrack?.uri ?: "")
//                                 }

//                                 val track = it.track
//                                 titlealbum.text = track.name
//                                 titlealbum.isSelected = true
//                                 groupalbum.text =
//                                     track.artists.joinToString(separator = ", ") { artist -> artist.name }
//                                 groupalbum.isSelected = true
//                                 textbpm2.isSelected = true
//                                 Glide.with(requireContext()).load(
//                                     track.imageUri.raw?.replace(
//                                         "spotify:image:", "https://i.scdn.co/image/"
//                                     )
//                                 ).into(imagelistening)
//                                 timeend.text = formatMilliseconds(track.duration)
//                                 seekbar.max = (track.duration / 1000).toInt()
                                if (!isUserSeeking) {
                                    seekbar.progress = (it.playbackPosition / 1000).toInt()
                                }
                                if (it.isPaused) {
                                    imageplaysong.setImageResource(R.drawable.play_icon)
                                } else {
                                    imageplaysong.setImageResource(R.drawable.pause_icon)
                                }
                            }
                        }
                    }

                    override fun onFailure(throwable: Throwable) {
                        Timber.e("Failed to connect: " + throwable.message, throwable)
                    }
                })
        }
    }

    override fun setupListeners() {
        with(binding) {
            ibPlusBpm.setOnClickListener {
                viewModel.incrementHeartRate()
            }
            ibMinusBpm.setOnClickListener {
                viewModel.decrementHeartRate()
            }
            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    if (fromUser) {
                        spotifyAppRemote?.playerApi?.seekTo(progress * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    isUserSeeking = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
                    debounceRunnable = Runnable {
                        isUserSeeking = false
                    }
                    debounceHandler.postDelayed(debounceRunnable!!, 1200)
                }
            })
            stopsession.setOnClickListener {
                spotifyAppRemote?.playerApi?.pause()
                SpotifyAppRemote.disconnect(spotifyAppRemote)
                findNavController().navigate(R.id.action_listeningFragment_to_summaryFragment)
//                 val bundle = Bundle()
//                 bundle.putString("SESSION_ID", sessionId)
//                 findNavController().navigate(R.id.action_listeningFragment_to_summaryFragment, bundle)
            }
            imageplaysong.setOnClickListener {
                spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                    if (playerState.isPaused) {
                        spotifyAppRemote?.playerApi?.resume()
                    } else {
                        spotifyAppRemote?.playerApi?.pause()
                    }
                }?.setErrorCallback { throwable ->

                }
            }
            imagenextsong.setOnClickListener {
                spotifyAppRemote?.playerApi?.skipNext()
                updateFavoriteToggleState()
            }
            imagepresong.setOnClickListener {
                spotifyAppRemote?.playerApi?.skipPrevious()
                updateFavoriteToggleState()
            }

            toggleFavorite.setOnClickListener {
                currentTrack?.let { track ->
                    val id = track.uri.hashCode()
                    val albumName = track.album.name
                    val username = track.name
                    val avatarUrl =
                        track.imageUri.raw?.replace("spotify:image:", "https://i.scdn.co/image/")
                    if (avatarUrl != null) {
                        Timber.d("Toggling favorite for track: $username")
                        // Panggil fungsi insert atau remove dari ViewModel
                        CoroutineScope(Dispatchers.IO).launch {
                            val count = viewModel.checkUser(id)
                            withContext(Dispatchers.Main) {
                                if (count != null) {
                                    if (count > 0) {
                                        viewModel.remove(id)
                                        _isChecked = false
                                    } else {
                                        viewModel.insert(username, id, albumName, avatarUrl)
                                        _isChecked = true
                                    }
                                    toggleFavorite.isChecked = _isChecked
                                }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Image URL is null", Toast.LENGTH_SHORT)
                            .show()
                    }
                } ?: run {
                    Toast.makeText(requireContext(), "Track is null", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun updateFavoriteToggleState() {
        currentTrack?.let { track ->
            val id = track.uri.hashCode()
            viewModel.insertSong(track, sessionId)
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUser(id)
                withContext(Dispatchers.Main) {
                    if (count != null && count > 0) {
                        _isChecked = true
                    } else {
                        _isChecked = false
                    }
                    binding.toggleFavorite.isChecked = _isChecked
                }
            }
        }
    }

    override fun setupObservers() {
        with(binding) {

            playbackPositionTimer = Timer()
            playbackPositionTimer?.schedule(object : TimerTask() {
                override fun run() {
                    uiHandler.post {
                        spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                            val position = playerState.playbackPosition
                            timestart.text = formatMilliseconds(position)
                            if (!isUserSeeking) {
                                seekbar.progress = (playerState.playbackPosition / 1000).toInt()
                            }
                        }
                    }
                }
            }, 0, 1000)
            viewModel.currentHeartRate.observe(viewLifecycleOwner) {
                initialbpm.text = getString(R.string.bpmtext, it)
            }
        }
    }

    private fun generateSessionId(trackUri: String): String {
        // Example: Generate session ID using UUID
        return UUID.randomUUID().toString()
    }
    private fun formatMilliseconds(ms: Long): String =
        String.format(Locale.getDefault(), "%02d:%02d", (ms / 1000) / 60, (ms / 1000) % 60)
}
