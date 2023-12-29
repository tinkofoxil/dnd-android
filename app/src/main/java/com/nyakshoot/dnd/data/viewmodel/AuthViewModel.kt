package com.nyakshoot.dnd.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyakshoot.dnd.data.api.ApiHelper
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.model.regModel.RegRequest
import com.nyakshoot.dnd.data.viewmodel.state.AuthState
import com.nyakshoot.dnd.data.viewmodel.state.RegState
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val _regState = MutableStateFlow<RegState>(RegState.Loading)
    val regState: StateFlow<RegState> = _regState

    fun authUser(request: AuthRequest) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val authResponse = apiHelper.auth(request)
                _authState.value = AuthState.Success(authResponse)
                sharedPreferencesManager.saveVal("token", authResponse.access_token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun regUser(request: RegRequest){
        _regState.value = RegState.Loading
        viewModelScope.launch {
            try {
                val regResponse = apiHelper.register(request)
                _regState.value = RegState.Success(regResponse)
            } catch (e: Exception) {
                _regState.value = RegState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

}