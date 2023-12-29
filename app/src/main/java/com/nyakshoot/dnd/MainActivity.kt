package com.nyakshoot.dnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nyakshoot.dnd.data.api.ApiHelper
import com.nyakshoot.dnd.data.api.RetrofitBuilder
import com.nyakshoot.dnd.data.viewmodel.AuthViewModel
import com.nyakshoot.dnd.data.viewmodel.CharacterViewModel
import com.nyakshoot.dnd.data.viewmodel.UserViewModel
import com.nyakshoot.dnd.data.viewmodel.ViewModelFactory
import com.nyakshoot.dnd.ui.navigation.graphs.RootNavGraph
import com.nyakshoot.dnd.ui.theme.DNDTheme
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferencesManager = SharedPreferencesManager(this)

        val authViewModel: AuthViewModel by viewModels {
            ViewModelFactory(
                ApiHelper(
                    RetrofitBuilder.provideApiService(RetrofitBuilder.provideRetrofit()),
                    sharedPreferencesManager
                )
            )
        }

        val characterViewModel: CharacterViewModel by viewModels {
            ViewModelFactory(
                ApiHelper(
                    RetrofitBuilder.provideApiService(RetrofitBuilder.provideRetrofit()),
                    sharedPreferencesManager
                )
            )
        }

        val userViewModel: UserViewModel by viewModels {
            ViewModelFactory(
                ApiHelper(
                    RetrofitBuilder.provideApiService(RetrofitBuilder.provideRetrofit()),
                    sharedPreferencesManager
                )
            )
        }

        setContent {
            DNDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val systemUiController: SystemUiController = rememberSystemUiController()
                    systemUiController.isStatusBarVisible = false // Status bar
                    //systemUiController.isNavigationBarVisible = false // Navigation bar
                    //systemUiController.isSystemBarsVisible = false // Status & Navigation bars

                    RootNavGraph(
                        navController = rememberNavController(),
                        authViewModel,
                        characterViewModel,
                        userViewModel,
                        sharedPreferencesManager
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DNDTheme {
        Greeting("Android")
    }
}