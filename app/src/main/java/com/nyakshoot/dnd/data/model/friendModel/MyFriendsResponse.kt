package com.nyakshoot.dnd.data.model.friendModel

import com.google.gson.annotations.SerializedName

data class MyFriendsResponse(
    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : List<Results> = emptyList()
)
