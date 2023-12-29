package com.nyakshoot.dnd.ui.screens.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nyakshoot.dnd.MainActivity
import com.nyakshoot.dnd.data.model.friendModel.User
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AddFriendState
import com.nyakshoot.dnd.data.viewmodel.state.AllMyFriendsState
import com.nyakshoot.dnd.data.viewmodel.state.UserCharactersState
import com.nyakshoot.dnd.ui.components.CharacterCard

@Composable
fun UserScreen(
    user: User,
    characterViewModel: CharacterViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController,
    topAppBarTitle: MutableState<String>
) {
    val userCharacterState by characterViewModel.userCharactersState.collectAsState(
        UserCharactersState.Loading
    )
    val allMyFriendsState by userViewModel.allMyFriendsState.collectAsState(initial = AllMyFriendsState.Loading)
    val addFriendsState by userViewModel.addFriendState.collectAsState(initial = AddFriendState.Loading)
    val friendsState = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }
    topAppBarTitle.value = user.username.toString()

    LaunchedEffect(userCharacterState, allMyFriendsState, addFriendsState) {
        when (userCharacterState) {
            is UserCharactersState.Loading -> {
                characterViewModel.getUserCharacter(
                    user.pk!!.toInt()
                )
            }
            is UserCharactersState.Success -> {
                loading.value = true
            }
            is UserCharactersState.Error -> {}
        }
        when (allMyFriendsState) {
            is AllMyFriendsState.Loading -> {
                userViewModel.getMyFriends()
            }
            is AllMyFriendsState.Success -> {
                for (result in userViewModel.allMyFriendsList) {
                    if (result.friend?.pk == user.pk)
                        friendsState.value = true
                }
                loading.value = true
            }
            is AllMyFriendsState.Error -> {}
        }
        when (addFriendsState) {
            is AddFriendState.Loading -> {}
            is AddFriendState.Success -> {
                friendsState.value = true
            }
            is AddFriendState.Error -> {}
        }
    }
    if (loading.value) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF555555)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if (!friendsState.value)
                            userViewModel.addFriend(user.pk!!)
                        else {
                            userViewModel.deleteFriend(user.pk!!)
                            friendsState.value = false
                        }
                    }
                ) {
                    if (friendsState.value) {
                        Text(
                            text = "Удалить из друзей",
                            style = MaterialTheme.typography.h4,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Добавить в друзья",
                            style = MaterialTheme.typography.h4,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(characterViewModel.userCharactersList.size) { item ->
                CharacterCard(
                    characterViewModel.userCharactersList[item],
                    navController,
                    topAppBarTitle
                )
            }
        }
    }
}