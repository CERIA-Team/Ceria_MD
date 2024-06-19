package com.ceria.capstone.ui.listening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class ListeningViewModel : ViewModel() {
    private val _currentHeartRate = MutableLiveData<Int>()
    val currentHeartRate: LiveData<Int> = _currentHeartRate

    fun setCurrentHeartRate(value: Int) {
        _currentHeartRate.value = value
    }
    fun incrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.plus(1)
    }

    fun decrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.minus(1)
    }
}