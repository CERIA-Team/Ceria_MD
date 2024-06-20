package com.ceria.capstone.ui.listening

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ceria.capstone.BuildConfig
import com.ceria.capstone.R
import com.ceria.capstone.data.Result
import com.ceria.capstone.databinding.FragmentListeningBinding
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.ui.common.BaseFragment
import com.ceria.capstone.utils.invisible
import com.ceria.capstone.utils.toastLong
import com.ceria.capstone.utils.visible
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

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
    private var flagFromUser = false
    private var currentTrack: Track? = null
    private var trackStartTime: Long = 0L
    private var avgBpm: Int = 0

    override fun initData() {
        viewModel.setCurrentHeartRate(args.initialBpm)
        avgBpm = args.initialBpm
        viewModel.initFlag.postValue(true)
    }

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
                        viewModel.getSongRecommendations(args.initialBpm, args.listenSessionId)
                        spotifyAppRemote?.let { remote ->
                            remote.playerApi.subscribeToPlayerState().setEventCallback {
                                val currentTrack: Track = it.track
                                if (currentTrack.uri != previousTrack?.uri) {
                                    val tempRecommendations = viewModel.recommendations.value
                                    if (tempRecommendations is Result.Success) {
                                        if ("spotify:track:" + tempRecommendations.data[2] == currentTrack.uri) {
                                            viewModel.getSongRecommendations(
                                                viewModel.currentHeartRate.value ?: args.initialBpm,
                                                args.listenSessionId
                                            )
                                        }
                                    }
                                    val id = currentTrack.uri.replace(
                                        "spotify:track:", ""
                                    )
                                    viewModel.checkFavorite(id)
                                    toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
                                        if (flagFromUser) {
                                            if (isChecked) {
                                                viewModel.addSongToFavorite(
                                                    SongDTO(
                                                        id,
                                                        currentTrack.name,
                                                        currentTrack.artists.joinToString(separator = ", ") { artist -> artist.name },
                                                        currentTrack.imageUri.raw?.replace(
                                                            "spotify:image:",
                                                            "https://i.scdn.co/image/"
                                                        ) ?: "Empty",
                                                        true
                                                    )
                                                )

                                            } else {
                                                viewModel.removeSongFromFavorite(
                                                    SongDTO(
                                                        id,
                                                        currentTrack.name,
                                                        currentTrack.artists.joinToString(separator = ", ") { artist -> artist.name },
                                                        currentTrack.imageUri.raw?.replace(
                                                            "spotify:image:",
                                                            "https://i.scdn.co/image/"
                                                        ) ?: "Empty",
                                                        false
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    titlealbum.text = currentTrack.name
                                    titlealbum.isSelected = true
                                    groupalbum.text =
                                        currentTrack.artists.joinToString(separator = ", ") { artist -> artist.name }
                                    groupalbum.isSelected = true
                                    tvNextQueue.isSelected = true
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
                                    viewModel.getNextQueue()
                                    previousTrack = currentTrack
                                    this@ListeningFragment.currentTrack = currentTrack
                                }
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
                        trackStartTime = System.currentTimeMillis()
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
                val trackDuration = System.currentTimeMillis() - trackStartTime
                val formattedDuration = formatMilliseconds(trackDuration)
                SpotifyAppRemote.disconnect(spotifyAppRemote)
                findNavController().navigate(
                    ListeningFragmentDirections.actionListeningFragmentToSummaryFragment(
                        args.listenSessionId, avgBpm.toString(), formattedDuration
                    )
                )
            }

            imageplaysong.setOnClickListener {
                spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                    if (playerState.isPaused) {
                        spotifyAppRemote?.playerApi?.resume()
                    } else {
                        spotifyAppRemote?.playerApi?.pause()
                    }
                }
            }
            imagenextsong.setOnClickListener {
                spotifyAppRemote?.playerApi?.skipNext()
            }
            imagepresong.setOnClickListener {
                spotifyAppRemote?.playerApi?.skipPrevious()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun setupObservers() {
        with(binding) {
            playbackPositionTimer = Timer()
            playbackPositionTimer?.schedule(
                object : TimerTask() {
                    override fun run() {
                        uiHandler.post {
                            val inc = viewModel.currentHeartRate.value ?: 0
                            if (inc != 0) {
                                avgBpm += inc
                                avgBpm /= 2
                            }
                            spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                                val tempNext = viewModel.nextQueue.value
                                if (tempNext is Result.Success) {
                                    if ("spotify:track:" + tempNext.data.id == playerState.track.uri) {
                                        viewModel.getNextQueue()
                                    }
                                }
                                val position = playerState.playbackPosition
                                timestart.text = formatMilliseconds(position)
                                if (!isUserSeeking) {
                                    seekbar.progress = (playerState.playbackPosition / 1000).toInt()
                                }
                            }
                        }
                    }
                }, 0, 1000
            )
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it is Result.Success) {
                    flagFromUser = false
                    toggleFavorite.isChecked = it.data
                    flagFromUser = true
                }
            }
            viewModel.currentHeartRate.observe(viewLifecycleOwner) {
                initialbpm.text = getString(R.string.bpmtext, it)
            }
            viewModel.nextQueue.observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        with(binding) {
                            tvNextQueue.text = it.data.title + " - " + it.data.artist
                            Glide.with(requireContext()).load(
                                it.data.imageUrl
                            ).placeholder(
                                ContextCompat.getDrawable(
                                    requireContext(), R.drawable.placeholder_song
                                )
                            ).error(
                                ContextCompat.getDrawable(
                                    requireContext(), R.drawable.placeholder_song
                                )
                            ).into(ivNext)
                            layoutNextQueue.visible()
                        }
                    }

                    else -> {
                        binding.layoutNextQueue.invisible()
                    }
                }
            }
            viewModel.recommendations.observe(viewLifecycleOwner) {
                when (it) {
                    Result.Empty -> {}
                    is Result.Error -> {
                        if (viewModel.initFlag.value != false) {
                            requireContext().toastLong(it.error)
                            findNavController().popBackStack()
                        }
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        if (viewModel.initFlag.value!!) {
                            spotifyAppRemote?.playerApi?.play("spotify:track:${it.data[0]}")
                            spotifyAppRemote?.playerApi?.queue("spotify:track:${it.data[1]}")
                            spotifyAppRemote?.playerApi?.queue("spotify:track:${it.data[2]}")
                        } else {
                            spotifyAppRemote?.playerApi?.queue("spotify:track:${it.data[0]}")
                            spotifyAppRemote?.playerApi?.queue("spotify:track:${it.data[1]}")
                            spotifyAppRemote?.playerApi?.queue("spotify:track:${it.data[2]}")
                        }
                        viewModel.getNextQueue()
                        viewModel.initFlag.postValue(false)
                    }
                }
            }
        }
    }

    private fun formatMilliseconds(ms: Long): String =
        String.format(Locale.getDefault(), "%02d:%02d", (ms / 1000) / 60, (ms / 1000) % 60)
}
