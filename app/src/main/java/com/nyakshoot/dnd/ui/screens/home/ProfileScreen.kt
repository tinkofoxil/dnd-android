package com.nyakshoot.dnd.ui.screens.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nyakshoot.dnd.MainActivity
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AllMyFriendsState
import com.nyakshoot.dnd.data.viewmodel.state.UserCharactersState
import com.nyakshoot.dnd.ui.components.FriendCard
import com.nyakshoot.dnd.ui.components.SmallCharacterCard
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel,
    characterViewModel: CharacterViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
    navController: NavHostController,
    topAppBarTitle: MutableState<String>
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val allMyFriendsState by userViewModel.allMyFriendsState.collectAsState(initial = AllMyFriendsState.Loading)
    val userCharacterState by characterViewModel.userCharactersState.collectAsState(initial = UserCharactersState.Loading)
    val loading = remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(userCharacterState, allMyFriendsState) {
        when (userCharacterState) {
            is UserCharactersState.Loading -> {
                Log.d("penis", sharedPreferencesManager.getVal("me_id").toString())
                characterViewModel.getUserCharacter(
                    sharedPreferencesManager.getVal("me_id")?.toInt()!!
                )
            }

            is UserCharactersState.Success -> {

            }

            is UserCharactersState.Error -> {

            }
        }
        when (allMyFriendsState) {
            is AllMyFriendsState.Loading -> {
                Log.d("8=D", userCharacterState.toString())
                userViewModel.getMyFriends()
            }

            is AllMyFriendsState.Success -> {
                loading.value = true
            }

            is AllMyFriendsState.Error -> {

            }
        }
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1000)
            characterViewModel.getUserCharacter(
                sharedPreferencesManager.getVal("me_id")?.toInt()!!
            )
            userViewModel.getMyFriends()
            refreshing = false
        }
    }

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            loading.value = false
        }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .fillMaxHeight(0.92f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading.value) {
                Log.d("8=D", loading.value.toString())
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Персонажи",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier
                        .width(350.dp)
                        .height(230.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF555555)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(characterViewModel.userCharactersList.size) { item ->
                        SmallCharacterCard(
                            characterViewModel.userCharactersList[item],
                            navController,
                            topAppBarTitle
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Друзья",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier
                        .width(300.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF555555)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(userViewModel.allMyFriendsList.size) { item ->
                        FriendCard(
                            userViewModel.allMyFriendsList[item].friend,
                            navController,
                            characterViewModel
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        sharedPreferencesManager.logOut()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        activity.finish()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
                ) {
                    Text(
                        text = "Выйти из аккаунта",
                        style = MaterialTheme.typography.h4,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}