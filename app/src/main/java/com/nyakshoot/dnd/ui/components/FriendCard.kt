package com.nyakshoot.dnd.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.nyakshoot.dnd.data.model.friendModel.Friend
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.ui.navigation.graphs.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FriendCard(
    friend: Friend?,
    navController: NavHostController,
    characterViewModel: CharacterViewModel
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(200.dp)
            .height(40.dp),
        onClick = {
            characterViewModel.setDefaultUserCharacterState()
            navController.navigate(Screen.User.route.replace("{user}", Gson().toJson(friend)))
        },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {
        Text(
            text = friend?.username!!,
            textAlign = TextAlign.Center
        )
    }
}