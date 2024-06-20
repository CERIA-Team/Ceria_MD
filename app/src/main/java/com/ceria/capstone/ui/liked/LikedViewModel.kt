package com.ceria.capstone.ui.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.FavoriteUseCase
import com.ceria.capstone.domain.usecase.GetFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val favoriteUseCase: FavoriteUseCase
) :
    ViewModel() {
    private val _songs = MutableLiveData<Result<List<SongDTO>>>()
    val songs = _songs as LiveData<Result<List<SongDTO>>>

    fun getFavoriteSongs() {
        viewModelScope.launch {
            getFavoriteSongsUseCase.getFavoriteSongs().asFlow().collect {
                _songs.postValue(it)
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
