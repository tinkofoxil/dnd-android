package com.nyakshoot.dnd.data.model.characterModel

data class NewCharacterRequest(
    val name: String,
    val age: String,
    val gender: String,
    val race: String,
    val class_name: String,
    val level: String,
    val charisma: String,
    val description: String,
    val strength: String,
    val dexterity: String,
    val constitution: String,
    val intelligence: String,
    val wisdom: String,
)
