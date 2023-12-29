package com.nyakshoot.dnd.ui.navigation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nyakshoot.dnd.R
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AddFriendState
import com.nyakshoot.dnd.data.viewmodel.state.UserCharactersState
import com.nyakshoot.dnd.ui.navigation.graphs.Screen
import com.nyakshoot.dnd.utils.SharedPreferencesManager

@Composable
fun HomeTopAppBar(
    navController: NavController,
    topAppBarTitle: MutableState<String>,
    sharedPreferencesManager: SharedPreferencesManager,
    characterViewModel: CharacterViewModel
) {
    val listItems = listOf(
        Screen.Characters,
        Screen.CreateCharacter,
        Screen.Profile
    )

    val elevation = remember { mutableStateOf(1.dp) }

    TopAppBar(
        elevation = elevation.value
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            if (currentRoute == Screen.CharacterDetail.route || currentRoute == Screen.User.route) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.popBackStack()
                            if (currentRoute == Screen.User.route)
                                characterViewModel.setDefaultUserCharacterState()
                        },
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }

            listItems.forEach { item ->
                if (item.route == currentRoute) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h2,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    elevation.value = 1.dp
                }
            }

            if (currentRoute == Screen.CharacterDetail.route || currentRoute == Screen.User.route) {
                Text(
                    modifier = Modifier.weight(0.2f),
                    text = topAppBarTitle.value,
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Icon( //Костыль
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                    tint = MaterialTheme.colors.primary
                )
                elevation.value = 0.dp
            } else if (currentRoute == Screen.Profile.route) {
                Text(
                    text = sharedPreferencesManager.getVal("me_name").toString(),
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                elevation.value = 0.dp
            }
        }
    }
}