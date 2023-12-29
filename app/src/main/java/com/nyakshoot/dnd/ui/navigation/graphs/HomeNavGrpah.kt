package com.nyakshoot.dnd.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.nyakshoot.dnd.data.model.Character
import com.nyakshoot.dnd.data.model.friendModel.User
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.ui.screens.home.CharacterDetailScreen
import com.nyakshoot.dnd.ui.screens.home.CharactersScreen
import com.nyakshoot.dnd.ui.screens.home.CreateCharacterScreen
import com.nyakshoot.dnd.ui.screens.home.ProfileScreen
import com.nyakshoot.dnd.ui.screens.home.UserScreen
import com.nyakshoot.dnd.utils.SharedPreferencesManager


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    characterViewModel: CharacterViewModel,
    userViewModel: UserViewModel,
    bottomBarState: MutableState<Boolean>,
    topAppBarTitle: MutableState<String>,
    sharedPreferencesManager: SharedPreferencesManager
) {
    NavHost(
        navController,
        startDestination = Screen.CreateCharacter.route
    ) {
        composable(Screen.CreateCharacter.route) {
            bottomBarState.value = true
            CreateCharacterScreen(characterViewModel)
        }
        composable(Screen.Characters.route) {
            bottomBarState.value = true
            CharactersScreen(characterViewModel, navController, topAppBarTitle)
        }
        composable(Screen.Profile.route) {
            bottomBarState.value = true
            ProfileScreen(
                userViewModel,
                characterViewModel,
                sharedPreferencesManager,
                navController,
                topAppBarTitle
            )
        }
        composable(Screen.CharacterDetail.route) {
            bottomBarState.value = false
            it.arguments?.getString("character")?.let { json ->
                val character = Gson().fromJson(json, Character::class.java)
                CharacterDetailScreen(
                    character,
                    sharedPreferencesManager,
                    navController,
                    characterViewModel
                )
            }
        }
        composable(Screen.User.route) {
            bottomBarState.value = false
            it.arguments?.getString("user")?.let { json ->
                val user = Gson().fromJson(json, User::class.java)
                UserScreen(
                    user,
                    characterViewModel,
                    userViewModel,
                    navController,
                    topAppBarTitle
                )
            }
        }
    }
}