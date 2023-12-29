package com.nyakshoot.dnd.data.api

import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.model.characterModel.NewCharacterRequest
import com.nyakshoot.dnd.data.model.friendModel.Friend
import com.nyakshoot.dnd.data.model.regModel.RegRequest
import com.nyakshoot.dnd.utils.SharedPreferencesManager
import javax.inject.Inject


class ApiHelper @Inject constructor(
    private val apiService: ApiService,
    val sharedPreferencesManager: SharedPreferencesManager
) {

    // регистрация пользователя
    suspend fun register(request: RegRequest) = apiService.register(request)

    //авторизация
    suspend fun auth(request: AuthRequest) = apiService.auth(request)

    suspend fun createCharacter(request: NewCharacterRequest) =
        apiService.createCharacter("Bearer " + sharedPreferencesManager.getVal("token"), request)

    //Получить инфу о юзере
    suspend fun getMe() =
        apiService.getMe("Bearer " + sharedPreferencesManager.getVal("token"))

    suspend fun getAllCharacters() = apiService.getAllCharacters()

    suspend fun getAllMyFriends() =
        apiService.getAllMyFriends("Bearer " + sharedPreferencesManager.getVal("token"))

    suspend fun getUserCharacter(userId: Int) = apiService.getUserCharacters(userId)

    suspend fun deleteCharacter(characterId: Int) = apiService.deleteCharacter(
        "Bearer " + sharedPreferencesManager.getVal("token"),
        characterId
    )

    suspend fun addFriend(friendId: Int) =
        apiService.addFriend("Bearer " + sharedPreferencesManager.getVal("token"), friendId)

    suspend fun deleteFriend(friendId: Int) =
        apiService.deleteFriend("Bearer " + sharedPreferencesManager.getVal("token"), friendId)
}