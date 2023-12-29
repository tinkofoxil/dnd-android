package com.nyakshoot.dnd.utils

import android.content.Context
import android.content.SharedPreferences
import com.nyakshoot.dnd.R
import com.nyakshoot.dnd.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext val context: Context) {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = appContext.getSharedPreferences(appContext.resources.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

    fun saveVal(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }
    fun getVal(key: String): String? {
        return preferences.getString(key, null)
    }
    fun delVal(key: String){
        preferences.edit().remove(key).apply()
    }
    fun logOut(){
        delVal("token")
        delVal("me_id")
        delVal("me_name")
        delVal("me_email")
    }
    fun saveMe(user: User){
        saveVal("me_id", user.id.toString())
        saveVal("me_name", user.username)
        saveVal("me_email", user.email)
    }
}

