package com.example.productsStore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsApi(
    val title: String,
    val description: String,
    @SerialName("images") val imageUrls: List<String>,
    val rating: Float,
    val price: Float,
    val weight: Int,
    val availabilityStatus: String,
    val warrantyInformation: String
)
