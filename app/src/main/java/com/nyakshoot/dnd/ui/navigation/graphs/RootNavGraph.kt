package com.nyakshoot.dnd.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyakshoot.dnd.data.viewmodel.AuthViewModel
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.ui.screens.home.HomeScreen
import com.nyakshoot.dnd.utils.SharedPreferencesManager

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    characterViewModel: CharacterViewModel,
    userViewModel: UserViewModel,
    sharedPreferencesManager: SharedPreferencesManager
) {
    NavHost(
        navController = navController,
        startDestination = Graph.AuthGraph.route,
        route = Graph.RootGraph.route
    )
    {
        authNavGraph(navController, authViewModel, userViewModel)
        composable(Graph.HomeGraph.route) {
            HomeScreen(characterViewModel, userViewModel, sharedPreferencesManager)
        }
    }
}