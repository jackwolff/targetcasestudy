package com.target.targetcasestudy.database.dataobjects

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("amount_in_cents")
    val amountInCents: Int,
    @SerializedName("current_symbol")
    val currencySymbol: String,
    @SerializedName("display_string")
    val displayString: String
)