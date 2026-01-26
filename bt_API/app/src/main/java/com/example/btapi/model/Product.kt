package com.example.btapi.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int, // DummyJSON trả về id là số
    @SerializedName("title") val name: String, // DummyJSON dùng "title"
    @SerializedName("thumbnail") val image: String, // DummyJSON dùng "thumbnail"
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double // DummyJSON trả về price là số
)
