package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.friendModel.MyFriendsResponse

sealed class AllMyFriendsState{
    object Loading : AllMyFriendsState()
    data class Success(val myFriendsResponse: MyFriendsResponse) : AllMyFriendsState()
    data class Error(val message: String) : AllMyFriendsState()
}
