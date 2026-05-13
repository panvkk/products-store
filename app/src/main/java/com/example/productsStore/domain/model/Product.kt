package com.example.productsStore.domain.model

data class Product(
    val id: Int,
    val productTitle: String,
    val price: Float,
    val brand: String?
)
