package com.nyakshoot.dnd.data.model.characterModel

data class NewCharacterResponse(
    val pk: Int,
    val name: String,
    val age: Int,
    val gender: String,
    val image: Boolean,
    val race: String,
    val class_name: String,
    val level: Int,
    val charisma: Int,
    val description: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val user: String,
    val user_id: Int
)
