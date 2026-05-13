package com.example.productsStore.presentation.model

data class ProductUiModel(
    val id: Int,
    val productTitle: String,
    val priceInUSD: Float,
    val brand: String?
)
