package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.characterModel.NewCharacterResponse

sealed class NewCharacterState {
    object Loading : NewCharacterState()
    data class Success(val newCharacterResponse: NewCharacterResponse) : NewCharacterState()
    data class Error(val message: String) : NewCharacterState()
}
