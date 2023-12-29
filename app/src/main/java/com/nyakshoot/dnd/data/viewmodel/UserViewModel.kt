package com.nyakshoot.dnd.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyakshoot.dnd.data.api.ApiHelper
import com.nyakshoot.dnd.data.model.Character
import com.nyakshoot.dnd.data.model.User
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.model.friendModel.Results
import com.nyakshoot.dnd.data.viewmodel.state.AddFriendState
import com.nyakshoot.dnd.data.viewmodel.state.AllCharactersState
import com.nyakshoot.dnd.data.viewmodel.state.AllMyFriendsState
import com.nyakshoot.dnd.data.viewmodel.state.AuthState
import com.nyakshoot.dnd.data.viewmodel.state.MeState
import com.nyakshoot.dnd.data.viewmodel.state.NewCharacterState
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _meState = MutableStateFlow<MeState>(MeState.Loading)
    val meState: StateFlow<MeState> = _meState

    private val _allMyFriendsState = MutableStateFlow<AllMyFriendsState>(AllMyFriendsState.Loading)
    val allMyFriendsState: StateFlow<AllMyFriendsState> = _allMyFriendsState
    private var _allMyFriendsList = mutableStateOf<List<Results>>(emptyList())
    val allMyFriendsList: List<Results>
        get() = _allMyFriendsList.value

    private val _addFriendState = MutableStateFlow<AddFriendState>(AddFriendState.Loading)
    val addFriendState: StateFlow<AddFriendState> = _addFriendState

    fun getMe() {
        _meState.value = MeState.Loading
        viewModelScope.launch {
            try {
                val meResponse = apiHelper.getMe()
                sharedPreferencesManager.saveMe(meResponse)
                Log.d("penis", meResponse.toString())
                _meState.value = MeState.Success(meResponse)
            } catch (e: Exception) {
                _meState.value = MeState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun getMyFriends(){
        _allMyFriendsState.value = AllMyFriendsState.Loading
        viewModelScope.launch {
            try {
                val myFriendsResponse = apiHelper.getAllMyFriends()
                _allMyFriendsList.value = myFriendsResponse.results
                _allMyFriendsState.value = AllMyFriendsState.Success(myFriendsResponse)
            } catch (e: Exception) {
                _allMyFriendsState.value = AllMyFriendsState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun addFriend(friendId: Int){
        _addFriendState.value = AddFriendState.Loading
        viewModelScope.launch {
            try {
                val addFriendResponse = apiHelper.addFriend(friendId)
                _addFriendState.value = AddFriendState.Success(addFriendResponse)
            } catch (e: Exception) {
                _addFriendState.value = AddFriendState.Error(e.message ?: "Возникла ошибка")
            }
        }
    }

    fun deleteFriend(friendId: Int){
        viewModelScope.launch {
            try {
                apiHelper.deleteFriend(friendId)
            } catch (e: Exception) {
                Log.d("Error", "error")
            }
        }
    }
}