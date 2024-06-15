package com.ceria.capstone.ui.setting

import androidx.lifecycle.ViewModel
import com.ceria.capstone.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun logout() = logoutUseCase.logout()
}