package com.target.targetcasestudy.database.dataobjects

import com.google.gson.annotations.SerializedName

data class ItemNotFoundResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)