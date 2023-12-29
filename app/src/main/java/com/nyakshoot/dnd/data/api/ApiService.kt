package com.nyakshoot.dnd.data.api

import com.nyakshoot.dnd.data.model.User
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthRequest
import com.nyakshoot.dnd.data.model.characterModel.authModel.AuthResponse
import com.nyakshoot.dnd.data.model.characterModel.AllCharactersResponse
import com.nyakshoot.dnd.data.model.characterModel.NewCharacterRequest
import com.nyakshoot.dnd.data.model.characterModel.NewCharacterResponse
import com.nyakshoot.dnd.data.model.characterModel.UserCharacterResponse
import com.nyakshoot.dnd.data.model.friendModel.AddFriendResponse
import com.nyakshoot.dnd.data.model.friendModel.MyFriendsResponse
import com.nyakshoot.dnd.data.model.regModel.RegRequest
import com.nyakshoot.dnd.data.model.regModel.RegResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/users/")
    suspend fun register(@Body request: RegRequest): RegResponse

    @POST("auth/jwt/create")
    suspend fun auth(@Body request: AuthRequest): AuthResponse

    @POST("profile/")
    suspend fun createCharacter(
        @Header("Authorization") token: String,
        @Body request: NewCharacterRequest
    ): NewCharacterResponse

    @GET("auth/users/me")
    suspend fun getMe(@Header("Authorization") token: String): User

    @GET("profile/?limit=1000&offset=0")
    suspend fun getAllCharacters(): AllCharactersResponse

    @GET("users/friends/?limit=1000&offset=0")
    suspend fun getAllMyFriends(@Header("Authorization") token: String): MyFriendsResponse

    @GET("user/{id}/profiles/?limit=1000&offset=0")
    suspend fun getUserCharacters(@Path("id") id: Int): UserCharacterResponse

    @DELETE("profile/{id}/")
    suspend fun deleteCharacter(@Header("Authorization") token: String, @Path("id") id: Int)

    @POST("users/{id}/friend/")
    suspend fun addFriend(@Header("Authorization") token: String, @Path("id") id: Int): AddFriendResponse

    @DELETE("users/{id}/friend/")
    suspend fun deleteFriend(@Header("Authorization") token: String, @Path("id") id: Int)
}
