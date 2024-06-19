package com.ceria.capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.domain.model.ProfileDTO
import com.ceria.capstone.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.GetFavoriteSongsUseCase
import timber.log.Timber

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase
) : ViewModel() {
    private val _profileResponse = MutableLiveData<Result<ProfileDTO>>()
    val profileResponse = _profileResponse as LiveData<Result<ProfileDTO>>
    private val _countFavorite = MutableLiveData<Result<List<SongDTO>>>()
    val countFavorite = _countFavorite as LiveData<Result<List<SongDTO>>>

    fun getFavoriteSongs() {
        viewModelScope.launch {
            getFavoriteSongsUseCase.getFavoriteSongs().asFlow().collect {
                _countFavorite.postValue(it)
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.getProfile().asFlow().collect {
                _profileResponse.postValue(it)
            }
        }
    }
}