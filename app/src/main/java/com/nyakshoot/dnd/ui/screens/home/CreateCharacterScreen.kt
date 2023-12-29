package com.nyakshoot.dnd.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.model.characterModel.NewCharacterRequest
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AuthState
import com.nyakshoot.dnd.data.viewmodel.state.NewCharacterState
import com.nyakshoot.dnd.ui.components.CharacterSkills
import com.nyakshoot.dnd.ui.components.CreateCharacterTextField
import com.nyakshoot.dnd.ui.components.GenderDropMenu
import com.nyakshoot.dnd.ui.navigation.graphs.Graph

@Composable
fun CreateCharacterScreen(
    characterViewModel: CharacterViewModel
) {
    //TODO
    //Обработка ошибок

    val name = remember { mutableStateOf("") }
    val className = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val race = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("0") }
    val level = remember { mutableStateOf(1) }
    val charisma = remember { mutableStateOf(1) }
    val strength = remember { mutableStateOf(1) }
    val dexterity = remember { mutableStateOf(1) }
    val constitution = remember { mutableStateOf(1) }
    val intelligence = remember { mutableStateOf(1) }
    val wisdom = remember { mutableStateOf(1) }

    val context = LocalContext.current
    val characterState by characterViewModel.newCharacterState.collectAsState(initial = NewCharacterState.Loading)

    LaunchedEffect(characterState) {
        when (characterState) {
            is NewCharacterState.Loading -> {

            }
            is NewCharacterState.Success -> {
                Toast.makeText(context, "Новый персонаж создан!", Toast.LENGTH_LONG).show()
            }
            is NewCharacterState.Error -> {
                Toast.makeText(context, "Ошибка!", Toast.LENGTH_LONG).show()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.911f)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CreateCharacterTextField(title = "Имя: ", name)
            CreateCharacterTextField(title = "Класс: ", className)
            CreateCharacterTextField(title = "Описание: ", description)
            CreateCharacterTextField(title = "Раса: ", race)
            CreateCharacterTextField(title = "Возраст: ", age)
            GenderDropMenu(gender)
            CharacterSkills(
                level,
                charisma,
                strength,
                dexterity,
                constitution,
                intelligence,
                wisdom
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .height(57.dp)
                    .width(161.dp),
                onClick = {
                    characterViewModel.createCharacter(NewCharacterRequest(
                        name.value,
                        age.value,
                        gender.value,
                        race.value,
                        className.value,
                        level.value.toString(),
                        charisma.value.toString(),
                        description.value,
                        strength.value.toString(),
                        dexterity.value.toString(),
                        constitution.value.toString(),
                        intelligence.value.toString(),
                        wisdom.value.toString()
                    ))
                }
            ) {
                Text(
                    text = "Создать",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}