package com.nyakshoot.dnd.ui.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nyakshoot.dnd.data.viewmodel.AuthViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.ui.screens.auth.LoginScreen
import com.nyakshoot.dnd.ui.screens.auth.RegScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
)
{
    navigation(
        route = Graph.AuthGraph.route,
        startDestination = Screen.Login.route
    ){
        composable(Screen.Login.route){
            LoginScreen(navController, authViewModel, userViewModel)
        }
        composable(Screen.Reg.route){
            RegScreen(navController, authViewModel, userViewModel)
        }
    }
}
