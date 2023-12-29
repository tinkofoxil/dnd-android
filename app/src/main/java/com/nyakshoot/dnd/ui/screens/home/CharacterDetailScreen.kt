package com.nyakshoot.dnd.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.nyakshoot.dnd.R
import com.nyakshoot.dnd.data.model.Character
import com.nyakshoot.dnd.data.model.friendModel.User
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.ui.navigation.graphs.Screen
import com.nyakshoot.dnd.utils.SharedPreferencesManager

@Composable
fun CharacterDetailScreen(
    character: Character,
    sharedPreferencesManager: SharedPreferencesManager,
    navController: NavController,
    characterViewModel: CharacterViewModel
) {
    val buttonState = remember { mutableStateOf(false) }
    if (character.user_id == sharedPreferencesManager.getVal("me_id")?.toInt()) {
        buttonState.value = true
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(170.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(

            ) {
                TextInBorder("Уровень: " + character.level.toString())
                TextInBorder("Класс: " + character.class_name)
                TextInBorder("Раса: " + character.race)
                TextInBorder("Возраст: " + character.age.toString())
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier
                .clickable {
                    characterViewModel.setDefaultUserCharacterState()
                    navController.navigate(
                        Screen.User.route.replace(
                            "{user}",
                            Gson().toJson(User(character.user_id, character.user))
                        )
                    )
                },
            text = "Автор: " + character.user,
            style = MaterialTheme.typography.h4,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Divider(
            color = Color(0xFF555555),
            thickness = 4.dp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ParameterInBox(parameterName = "Харизма ", parameterValue = character.charisma)
            ParameterInBox(parameterName = "Сила ", parameterValue = character.strength)
            ParameterInBox(parameterName = "Ловкость ", parameterValue = character.dexterity)
            ParameterInBox(parameterName = "Телосложение ", parameterValue = character.constitution)
            ParameterInBox(parameterName = "Интелект ", parameterValue = character.intelligence)
            ParameterInBox(parameterName = "Мудрость ", parameterValue = character.wisdom)
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (buttonState.value) {
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .width(161.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    characterViewModel.setDefaultUserCharacterState()
                    characterViewModel.deleteCharacter(character.pk)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
            ) {
                Text(
                    text = "Удалить персонажа",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TextInBorder(text: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(3.dp)
            .width(200.dp)
            .border(3.dp, Color(0xFF555555), RoundedCornerShape(10.dp))
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = text,
            style = MaterialTheme.typography.h4,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ParameterInBox(parameterName: String, parameterValue: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 3.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 3.dp
                    )
                )
                .background(Color(0xFF555555))
                .width(200.dp)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = parameterName,
                style = MaterialTheme.typography.h4,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 3.dp,
                        topEnd = 10.dp,
                        bottomStart = 3.dp,
                        bottomEnd = 10.dp
                    )
                )
                .background(Color(0xFF555555))
                .width(40.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, top = 10.dp),
                text = parameterValue.toString(),
                style = MaterialTheme.typography.h4,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}