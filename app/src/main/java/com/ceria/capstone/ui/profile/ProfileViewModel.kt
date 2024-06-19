package com.ceria.capstone.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.roomfavorite.FavoriteDao
import com.ceria.capstone.data.roomfavorite.FavoriteDatabase
import com.ceria.capstone.domain.model.ProfileDTO
import com.ceria.capstone.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    application: Application

) : ViewModel() {
    private val _profileResponse = MutableLiveData<Result<ProfileDTO>>()
    val profileResponse = _profileResponse as LiveData<Result<ProfileDTO>>

    private val _favoriteCount = MutableLiveData<Int>()
    val favoriteCount: LiveData<Int> = _favoriteCount
    private val favoriteDao: FavoriteDao
    // Function to fetch profile data
    init {
        val favoriteDb = FavoriteDatabase.getDatabase(application)
        favoriteDao = favoriteDb.favoriteuserDao()
    }

        fun getProfile() {
            viewModelScope.launch {
                getProfileUseCase.getProfile().asFlow().collect {
                    _profileResponse.postValue(it)
                }
            }
        }

        // Function to get favorite count synchronously
        fun getFavoriteCount() {
            viewModelScope.launch {
                val count = withContext(Dispatchers.IO) {
                    favoriteDao.getFavoriteCountSync()
                }
                _favoriteCount.postValue(count)
            }
        }


}
