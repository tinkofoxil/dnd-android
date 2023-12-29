package com.nyakshoot.dnd.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyakshoot.dnd.data.api.ApiHelper

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(apiHelper, apiHelper.sharedPreferencesManager) as T
        }

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(apiHelper, apiHelper.sharedPreferencesManager) as T
        }

        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(apiHelper, apiHelper.sharedPreferencesManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}