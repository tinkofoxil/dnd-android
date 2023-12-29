package com.nyakshoot.dnd.data.model.characterModel.authModel

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access")
    val access_token: String,
    @SerializedName("refresh")
    val refresh_token: String
)