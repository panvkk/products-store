package com.example.productsStore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsApi(
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("images") val imageUrls: List<String>,
    @SerialName("rating") val rating: Float,
    @SerialName("price") val price: Float,
    @SerialName("weight") val weight: Int,
    @SerialName("availabilityStatus") val availabilityStatus: String,
    @SerialName("warrantyInformation") val warrantyInformation: String
)
