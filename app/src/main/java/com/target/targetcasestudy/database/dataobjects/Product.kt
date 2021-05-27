package com.target.targetcasestudy.database.dataobjects

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("aisle")
    val aisle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("regular_price")
    val regularPrice: Price,
    @SerializedName("sale_price")
    val salePrice: Price?,
)