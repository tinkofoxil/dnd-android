package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.characterModel.AllCharactersResponse

sealed class AllCharactersState{
    object Loading : AllCharactersState()
    data class Success(val allCharactersResponse: AllCharactersResponse) : AllCharactersState()
    data class Error(val message: String) : AllCharactersState()
}
