package com.ceria.capstone.ui.monitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.usecase.StartSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonitorViewModel @Inject constructor(private val startSessionUseCase: StartSessionUseCase) :
    ViewModel() {
    private val _initialHeartRate = MutableLiveData(70)
    val initialHeartRate: LiveData<Int> = _initialHeartRate

    private val _sessionResponse = MutableLiveData<Result<String>>()
    val sessionResponse: LiveData<Result<String>> = _sessionResponse

    fun incrementHeartRate() {
        _initialHeartRate.value = _initialHeartRate.value?.plus(1)
    }

    fun decrementHeartRate() {
        _initialHeartRate.value = _initialHeartRate.value?.minus(1)
    }

    fun startSession() {
        viewModelScope.launch {
            startSessionUseCase.startSession().asFlow().collect {
                _sessionResponse.postValue(it)
            }
        }
    }
}