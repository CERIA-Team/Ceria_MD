package com.ceria.capstone.ui.proto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ceria.capstone.databinding.ActivityProtoBinding
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

//Hanya CONTOH untuk nanti, tidak untuk production
class ProtoActivity : AppCompatActivity() {
    private val clientId = "<CLIENTID>" // kalo mau coba-coba nanti pc aku
    private val clientSecret = "<CLIENTSECRET>"
    private val REQUEST_CODE = 1337
    private val redirectUri = "ceriaauthresponse://callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private lateinit var binding: ActivityProtoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProtoBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        with(binding) {
            btnPlayPause.setOnClickListener {
                spotifyAppRemote?.let { remote ->
                    remote.playerApi.playerState
                        .setResultCallback { playerState ->
                            Log.d("STATE", playerState.isPaused.toString())
                            if (playerState.isPaused) {
                                remote.playerApi.resume()
                            } else {
                                remote.playerApi.pause()
                            }
                        }
                        .setErrorCallback { throwable -> }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val builder =
            AuthorizationRequest.Builder(clientId, AuthorizationResponse.Type.CODE, redirectUri)
        builder.setScopes(
            arrayOf(
                "streaming",
                "app-remote-control",
                "user-read-email",
                "user-read-recently-played"
            )
        )
        val request = builder.build()
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)

            when (response.type) {
                AuthorizationResponse.Type.CODE -> {
                    Log.d("AUTH_CODE", response.code)
                    exchangeCodeForTokens(response.code)
                }

                AuthorizationResponse.Type.ERROR -> {
                    Log.e("AUTH_ERROR", "Auth error: " + response.error)
                }

                else -> {
                }
            }
        }
    }

    private fun exchangeCodeForTokens(code: String) {
        val url = "https://accounts.spotify.com/api/token"
        val requestBody = FormBody.Builder()
            .add("grant_type", "authorization_code")
            .add("code", code)
            .add("redirect_uri", redirectUri)
            .add("client_id", clientId)
            .add("client_secret", clientSecret)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TOKEN_EXCHANGE", "Failed to exchange token: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val json = JSONObject(it.string())
                    val accessToken = json.getString("access_token")
                    val refreshToken = json.getString("refresh_token")
                    Log.d("ACCESS_TOKEN", accessToken)
                    Log.d("REFRESH_TOKEN", refreshToken)
                    runOnUiThread { connectToSpotify(accessToken) }
                }
            }
        })
    }

    private fun connectToSpotify(accessToken: String) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(false)
            .build()

        SpotifyAppRemote.connect(
            this,
            connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(appRemote: SpotifyAppRemote) {
                    spotifyAppRemote = appRemote
                    Log.d("MainActivity", "Connected! Yay!")
                    connected()
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("MainActivity", "Failed to connect: " + throwable.message, throwable)
                }
            })
    }

    private fun connected() {
        spotifyAppRemote?.let { remote ->
            // Play a playlist
            val playlistURI = "spotify:playlist:36OQoiRBoiON8bzdP2o2GU"
            remote.playerApi.play(playlistURI)
            // Subscribe to PlayerState
            remote.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            it.playerApi.pause()
            SpotifyAppRemote.disconnect(it)
        }
    }
}