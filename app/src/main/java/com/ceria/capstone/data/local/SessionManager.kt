package com.ceria.capstone.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(context: Context) {

    private val dataStore = context.dataStore
    suspend fun setCeriaToken(token: String) {
        dataStore.edit { preferences ->
            preferences[CERIA_TOKEN] = token
        }
    }

    suspend fun setSpotifyAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[SPOTIFY_ACCESS_TOKEN] = token
        }
    }

    suspend fun setSpotifyRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[SPOTIFY_REFRESH_TOKEN] = token
        }
    }

    suspend fun removeToken() {
        dataStore.edit { prefs ->
            prefs.remove(CERIA_TOKEN)
            prefs.remove(SPOTIFY_ACCESS_TOKEN)
            prefs.remove(SPOTIFY_REFRESH_TOKEN)
        }
    }


    val ceriaToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[CERIA_TOKEN]
    }
    val spotifyRefreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[SPOTIFY_REFRESH_TOKEN]
    }
    val spotifyAccessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[SPOTIFY_ACCESS_TOKEN]
    }

    companion object {
        private val Context.dataStore by preferencesDataStore("app_preferences")
        val CERIA_TOKEN = stringPreferencesKey("ceria_token")
        val SPOTIFY_ACCESS_TOKEN = stringPreferencesKey("spotify_access_token")
        val SPOTIFY_REFRESH_TOKEN = stringPreferencesKey("spotify_refresh_token")
    }

}