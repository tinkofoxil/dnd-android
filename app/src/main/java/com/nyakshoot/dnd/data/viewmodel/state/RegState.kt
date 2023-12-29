package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.regModel.RegResponse

sealed class RegState{
    object Loading : RegState()
    data class Success(val regResponse: RegResponse) : RegState()
    data class Error(val message: String) : RegState()
}
