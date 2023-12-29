package com.nyakshoot.dnd.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.ui.navigation.components.HomeTopAppBar
import com.nyakshoot.dnd.ui.navigation.components.MyBottomNavBar
import com.nyakshoot.dnd.ui.navigation.graphs.HomeNavGraph
import com.nyakshoot.dnd.utils.SharedPreferencesManager

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    characterViewModel: CharacterViewModel,
    userViewModel: UserViewModel,
    sharedPreferencesManager: SharedPreferencesManager
) {
    val navController = rememberNavController()

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topAppBarTitle = remember { (mutableStateOf("")) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            HomeTopAppBar(
                navController,
                topAppBarTitle,
                sharedPreferencesManager,
                characterViewModel
            )
        },
        bottomBar = {
            MyBottomNavBar(navController, bottomBarState)
        }
    ) {
        HomeNavGraph(
            navController,
            characterViewModel,
            userViewModel,
            bottomBarState,
            topAppBarTitle,
            sharedPreferencesManager
        )
    }
}