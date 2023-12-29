package com.nyakshoot.dnd.ui.navigation.graphs

import com.nyakshoot.dnd.R

sealed class Screen(val title: String, val iconId: Int, val route: String){
    object Login: Screen("", -1, "login_screen")
    object Reg: Screen("", -1, "reg_screen")
    object CreateCharacter: Screen("Создание персонажа", R.drawable.two_dices, "create_character_screen")
    object Characters: Screen("Персонажи", R.drawable.sword, "characters_screen")
    object Profile: Screen("", R.drawable.baseline_person_24, "profile_screen")
    object Home: Screen("", -1, "home_screen")
    object CharacterDetail: Screen("", -1, "character_detail_screen/{character}")
    object User: Screen("", -1, "user_screen/{user}")
}
