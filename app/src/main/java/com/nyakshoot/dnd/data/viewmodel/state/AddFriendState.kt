package com.nyakshoot.dnd.data.viewmodel.state

import com.nyakshoot.dnd.data.model.friendModel.AddFriendResponse

sealed class AddFriendState{
    object Loading : AddFriendState()
    data class Success(val addFriendResponse: AddFriendResponse) : AddFriendState()
    data class Error(val message: String) : AddFriendState()
}
