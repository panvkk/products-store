package com.example.productsStore.presentation.model

data class ProductDetailsUiModel(
    val productTitle: String,
    val description: String,
    val rating: Float,
    val priceInUSD: Float,
    val weight: Int,
    val availabilityStatus: String,
    val warrantyInformation: String,
    val isExpiredInfo: Boolean
)