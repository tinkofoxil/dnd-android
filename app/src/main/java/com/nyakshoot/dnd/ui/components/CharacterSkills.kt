package com.nyakshoot.dnd.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyakshoot.dnd.R

@Composable
fun CharacterSkills(
    level: MutableState<Int>,
    charisma: MutableState<Int>,
    strength: MutableState<Int>,
    dexterity: MutableState<Int>,
    constitution: MutableState<Int>,
    intelligence: MutableState<Int>,
    wisdom: MutableState<Int>
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primaryVariant)
            .border(BorderStroke(1.dp, Color(0xFFCCCCCC)))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Parameter(parameter = "Уровень: ${level.value}", level)
        Parameter(parameter = "Харизма: ${charisma.value}", charisma)
        Parameter(parameter = "Сила: ${strength.value}", strength)
        Parameter(parameter = "Ловкость: ${dexterity.value}", dexterity)
        Parameter(parameter = "Телосложение: ${constitution.value}", constitution)
        Parameter(parameter = "Интелект: ${intelligence.value}", intelligence)
        Parameter(parameter = "Мудрость: ${wisdom.value}", wisdom)
    }
}

@Composable
private fun Parameter(parameter: String, parameterValue: MutableState<Int>) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = parameter,
            style = MaterialTheme.typography.h4,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Row() {
            Button(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    if (parameterValue.value > 0) {
                        parameterValue.value--
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "minus",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    if (parameterValue.value < 10) {
                        parameterValue.value++
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "plus",
                    tint = Color.White
                )
            }
        }
    }
}
