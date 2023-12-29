package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.characterModel.UserCharacterResponse

sealed class UserCharactersState{
    object Loading : UserCharactersState()
    data class Success(val userCharacterResponse: UserCharacterResponse) : UserCharactersState()
    data class Error(val message: String) : UserCharactersState()
}
