package com.nyakshoot.dnd.data.model.friendModel

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("pk"     ) var pk     : Int?    = null,
    @SerializedName("user"   ) var user   : User?   = User(),
    @SerializedName("friend" ) var friend : Friend? = Friend()
)
