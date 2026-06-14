package com.example.productsStore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductApi(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("price") val price: Float,
    @SerialName("brand") val brand: String? = null
)