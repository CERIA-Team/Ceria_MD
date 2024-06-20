package com.ceria.capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.ProfileDTO
import com.ceria.capstone.domain.model.SessionDTO
import com.ceria.capstone.domain.usecase.GetAllSessionsUseCase
import com.ceria.capstone.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getAllSessionsUseCase: GetAllSessionsUseCase
) : ViewModel() {
    private val _profileResponse = MutableLiveData<Result<ProfileDTO>>()
    val profileResponse = _profileResponse as LiveData<Result<ProfileDTO>>

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.getProfile().asFlow().collect {
                _profileResponse.postValue(it)
            }
        }
    }

    private val _sessions = MutableLiveData<Result<List<SessionDTO>>>()
    val sessions = _sessions as LiveData<Result<List<SessionDTO>>>

    fun getSessions() {
        viewModelScope.launch {
            getAllSessionsUseCase.getAllSessions().asFlow().collect {
                _sessions.postValue(it)
            }
        }
    }
}