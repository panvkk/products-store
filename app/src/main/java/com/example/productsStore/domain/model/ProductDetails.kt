package com.example.productsStore.domain.model

data class ProductDetails(
    val title: String,
    val description: String,
    val rating: Float,
    val price: Float,
    val weight: Int,
    val availabilityStatus: String,
    val warrantyInformation: String,
    val isIrrelevantInfo: Boolean = false
)