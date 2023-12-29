package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.User

sealed class MeState{
    object Loading : MeState()
    data class Success(val user: User) : MeState()
    data class Error(val message: String) : MeState()
}
