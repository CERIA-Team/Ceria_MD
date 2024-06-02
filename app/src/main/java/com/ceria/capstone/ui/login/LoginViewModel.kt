package com.ceria.capstone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SpotifyToken
import com.ceria.capstone.domain.usecase.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getAccessTokenUseCase: GetAccessTokenUseCase) :
    ViewModel() {
    private val _tokenResponse = MutableLiveData<Result<SpotifyToken>>()
    val tokenResponse = _tokenResponse as LiveData<Result<SpotifyToken>>

    fun getAccessToken(code: String, redirectUri: String) {
        viewModelScope.launch {
            getAccessTokenUseCase.getAccessToken(code, redirectUri).asFlow().collect {
                _tokenResponse.postValue(it)
            }
        }
    }
}