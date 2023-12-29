package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthResponse

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val authResponse: AuthResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}
