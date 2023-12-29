package com.nyakshoot.dnd.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyakshoot.dnd.data.api.ApiHelper
import com.nyakshoot.dnd.data.model.Character
import com.nyakshoot.dnd.data.model.characterModel.NewCharacterRequest
import com.nyakshoot.dnd.data.viewmodel.state.AllCharactersState
import com.nyakshoot.dnd.data.viewmodel.state.NewCharacterState
import com.nyakshoot.dnd.data.viewmodel.state.UserCharactersState
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    //Создание нового персонажа
    private val _newCharacterState = MutableStateFlow<NewCharacterState>(NewCharacterState.Loading)
    val newCharacterState: StateFlow<NewCharacterState> = _newCharacterState

    //Список всех персонажей
    private val _allCharactersState = MutableStateFlow<AllCharactersState>(AllCharactersState.Loading)
    val allCharactersState: StateFlow<AllCharactersState> = _allCharactersState
    private var _allCharactersList = mutableStateOf<List<Character>>(emptyList())
    val allCharactersList: List<Character>
        get() = _allCharactersList.value

    //Список персонажей пользователя
    private val _userCharactersState = MutableStateFlow<UserCharactersState>(UserCharactersState.Loading)
    val userCharactersState: StateFlow<UserCharactersState> = _userCharactersState
    private var _userCharactersList = mutableStateOf<List<Character>>(emptyList())
    val userCharactersList: List<Character>
        get() = _userCharactersList.value

    fun createCharacter(request: NewCharacterRequest){
        _newCharacterState.value = NewCharacterState.Loading
        viewModelScope.launch {
            try {
                val newCharacterResponse = apiHelper.createCharacter(request)
                _newCharacterState.value = NewCharacterState.Success(newCharacterResponse)
            } catch (e: Exception) {
                _newCharacterState.value = NewCharacterState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun getAllCharacters(){
        _allCharactersState.value = AllCharactersState.Loading
        viewModelScope.launch {
            try {
                val allCharactersResponse = apiHelper.getAllCharacters()
                _allCharactersList.value = allCharactersResponse.results
                _allCharactersState.value = AllCharactersState.Success(allCharactersResponse)
            } catch (e: Exception) {
                _allCharactersState.value = AllCharactersState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun getUserCharacter(userId: Int){
        _userCharactersState.value = UserCharactersState.Loading
        viewModelScope.launch {
            try {
                val allCharactersResponse = apiHelper.getUserCharacter(userId)
                _userCharactersList.value = allCharactersResponse.results
                _userCharactersState.value = UserCharactersState.Success(allCharactersResponse)
            } catch (e: Exception) {
                _userCharactersState.value = UserCharactersState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun deleteCharacter(characterId: Int){
        viewModelScope.launch {
            try {
                apiHelper.deleteCharacter(characterId)
            } catch (e: Exception) {
                Log.d("Error", "error")
            }
        }
    }

    fun setDefaultUserCharacterState(){
        viewModelScope.launch {
            try {
                _userCharactersList.value = emptyList()
                _userCharactersState.value = UserCharactersState.Loading
            } catch (e: Exception) {
                _userCharactersState.value = UserCharactersState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }
}