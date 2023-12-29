package com.nyakshoot.dnd.data.model.friendModel

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("pk"       ) var pk       : Int?    = null,
    @SerializedName("username" ) var username : String? = null
)
