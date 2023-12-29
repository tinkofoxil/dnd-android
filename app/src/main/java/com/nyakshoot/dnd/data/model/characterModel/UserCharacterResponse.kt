package com.nyakshoot.dnd.data.model.characterModel

import com.google.gson.annotations.SerializedName
import com.nyakshoot.dnd.data.model.Character

data class UserCharacterResponse(
    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : List<Character> = listOf()
)
