package com.nyakshoot.dnd.ui.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nyakshoot.dnd.R
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.viewmodel.AuthViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.data.viewmodel.state.AuthState
import com.nyakshoot.dnd.data.viewmodel.state.MeState
import com.nyakshoot.dnd.ui.navigation.graphs.Graph
import com.nyakshoot.dnd.ui.navigation.graphs.Screen
import com.nyakshoot.dnd.utils.SharedPreferencesManager

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel, userViewModel: UserViewModel) {

    val sharedPreferencesManager = SharedPreferencesManager(LocalContext.current)
    /*
    TODO
    Обработка ошибок
     */
    val context = LocalContext.current

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val passwordVisibility = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    //Стейт авторизации пользователя
    val authState by authViewModel.authState.collectAsState(initial = AuthState.Loading)
    val meState by userViewModel.meState.collectAsState(initial = MeState.Loading)

    LaunchedEffect(authState, meState) {
        when (authState) {
            is AuthState.Loading -> {

            }
            is AuthState.Success -> {
                userViewModel.getMe()
            }
            is AuthState.Error -> {
                Toast.makeText(context, "Ошибка аунтификации!", Toast.LENGTH_LONG).show()
            }
        }
        when (meState) {
            is MeState.Loading -> {

            }
            is MeState.Success -> {
                navController.popBackStack()
                navController.navigate(Graph.HomeGraph.route)
            }
            is MeState.Error -> {
                Toast.makeText(context, "Ошибка аунтификации!", Toast.LENGTH_LONG).show()
            }
        }
    }

    if (sharedPreferencesManager.getVal("token") != null && authState == AuthState.Loading)
    {
        navController.popBackStack()
        navController.navigate(Graph.HomeGraph.route)
    }
    else
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Вход",
                style = MaterialTheme.typography.h1,
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Имя:",
                    style = MaterialTheme.typography.h4,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    modifier = Modifier
                        .focusable()
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                    ,
                    value = username.value,
                    onValueChange = { username.value = it },
                    textStyle = MaterialTheme.typography.h4.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusRequester.requestFocus() }
                    )
                )
                Text(
                    text = "Пароль:",
                    style = MaterialTheme.typography.h4,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(0.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                        .focusRequester(focusRequester),
                    value = password.value,
                    onValueChange = { password.value = it },
                    textStyle = MaterialTheme.typography.h4.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility.value = !passwordVisibility.value }
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = R.drawable.hide_pass),
                                contentDescription = "show-password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                modifier = Modifier
                    .height(57.dp)
                    .width(161.dp),
                onClick = {
                    authViewModel.authUser(AuthRequest(username.value, password.value))
                }
            ) {
                Text(
                    text = "Войти",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                modifier = Modifier.clickable {
                    //Регистрация
                    navController.navigate(Screen.Reg.route)
                },
                text = "Ещё не зарегистрированы?",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
