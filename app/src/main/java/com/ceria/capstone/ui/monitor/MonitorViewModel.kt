package com.ceria.capstone.ui.monitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MonitorViewModel : ViewModel() {
    private val _initialHeartRate = MutableLiveData(70)
    val initialHeartRate: LiveData<Int> = _initialHeartRate

    fun incrementHeartRate() {
        _initialHeartRate.value = _initialHeartRate.value?.plus(1)
    }

    fun decrementHeartRate() {
        _initialHeartRate.value = _initialHeartRate.value?.minus(1)
    }
}