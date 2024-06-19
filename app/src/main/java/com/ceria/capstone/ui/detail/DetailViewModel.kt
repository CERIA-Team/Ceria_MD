package com.ceria.capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.FavoriteUseCase
import com.ceria.capstone.domain.usecase.GetSessionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSessionDetailUseCase: GetSessionDetailUseCase,
    private val favoriteUseCase: FavoriteUseCase
) :
    ViewModel() {
    private val _songs = MutableLiveData<Result<List<SongDTO>>>()
    val songs: LiveData<Result<List<SongDTO>>> = _songs
    fun getSessionDetail(id: String) {
        viewModelScope.launch {
            getSessionDetailUseCase.getSessionDetail(id).asFlow().collect {
                _songs.value = it
            }
        }
    }

    fun addSongToFavorite(song: SongDTO) {
        viewModelScope.launch {
            favoriteUseCase.addSongToFavorite(song)
        }
    }

    fun removeSongFromFavorite(song: SongDTO) {
        viewModelScope.launch {
            favoriteUseCase.removeSongFromFavorite(song)
        }
    }
}