package com.ceria.capstone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SpotifyTokenDTO
import com.ceria.capstone.domain.usecase.CheckTokenUseCase
import com.ceria.capstone.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, private val checkTokenUseCase: CheckTokenUseCase
) : ViewModel() {
    private val _loginResponse = MutableLiveData<Result<SpotifyTokenDTO>>()
    val loginResponse = _loginResponse as LiveData<Result<SpotifyTokenDTO>>
    private val _tokenResponse = MutableLiveData<Result<String>>()
    val tokenResponse = _tokenResponse as LiveData<Result<String>>

    fun checkToken() {
        viewModelScope.launch {
            checkTokenUseCase.checkToken().asFlow().collect {
                _tokenResponse.postValue(it)
            }
        }
    }

    fun login(code: String, redirectUri: String) {
        viewModelScope.launch {
            loginUseCase.authLogin(code, redirectUri).asFlow().collect {
                _loginResponse.postValue(it)
            }
        }
    }
}