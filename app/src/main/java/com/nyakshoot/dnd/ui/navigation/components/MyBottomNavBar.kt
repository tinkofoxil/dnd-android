package com.nyakshoot.dnd.ui.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nyakshoot.dnd.ui.navigation.graphs.Screen

@Composable
fun MyBottomNavBar(navController: NavController, bottomBarState: MutableState<Boolean>) {
    val listItems = listOf(
        Screen.Characters,
        Screen.CreateCharacter,
        Screen.Profile
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation() {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            listItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(item.route) {
                                inclusive = true
                            }
                        }
                    },
                    icon = {
                        if (currentRoute == item.route) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterVertically)
                                    .clip(shape = RoundedCornerShape(size = 5.dp))
                                    .background(Color(0xFF555555))
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = "Icon"
                                )
                            }
                        } else {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = item.iconId),
                                contentDescription = "Icon"
                            )
                        }
                    }
                )
            }
        }
    }
}