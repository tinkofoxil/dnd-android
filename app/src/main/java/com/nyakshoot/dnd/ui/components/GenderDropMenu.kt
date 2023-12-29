package com.nyakshoot.dnd.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nyakshoot.dnd.R

@Composable
fun GenderDropMenu(selectedText: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Мужчина", "Женщина", "Некто")
    val selectedGender: Map<String, String> = mapOf(
        "Мужчина" to "M",
        "Женщина" to "W",
        "Некто" to "N"
    )

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(
        modifier = Modifier
            .padding(20.dp)
            .width(150.dp)
    )
    {
        OutlinedTextField(
            value = selectedText.value,
            onValueChange = { selectedText.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(
                text = stringResource(R.string.gender_label)
            ) },
            trailingIcon = {
                Icon(
                    icon,
                    "contentDescription",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText.value = label
                    selectedText.value = selectedGender[selectedText.value].toString()
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}