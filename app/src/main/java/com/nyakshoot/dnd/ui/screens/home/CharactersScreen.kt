package com.nyakshoot.dnd.ui.screens.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AllCharactersState
import com.nyakshoot.dnd.ui.components.CharacterCard
import kotlinx.coroutines.delay

@Composable
fun CharactersScreen(
    characterViewModel: CharacterViewModel,
    navController: NavHostController,
    topAppBarTitle: MutableState<String>
) {

    val allCharactersState by characterViewModel.allCharactersState.collectAsState(initial = AllCharactersState.Loading)
    val loading = remember { mutableStateOf(false) }

    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(allCharactersState) {
        when (allCharactersState) {
            is AllCharactersState.Loading -> {
                characterViewModel.getAllCharacters()
            }

            is AllCharactersState.Success -> {
                loading.value = true
            }

            is AllCharactersState.Error -> {

            }
        }
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            characterViewModel.getAllCharacters()
            refreshing = false
        }
    }

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            loading.value = false
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.911f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading.value) {
                items(characterViewModel.allCharactersList.size) { item ->
                    CharacterCard(
                        characterViewModel.allCharactersList[item],
                        navController,
                        topAppBarTitle
                    )
                }
            }
        }
    }
}