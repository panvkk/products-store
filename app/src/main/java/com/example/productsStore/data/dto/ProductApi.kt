package com.example.productsStore.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductApi(
    val id: Int,
    val title: String,
    val price: Float,
    val brand: String
)