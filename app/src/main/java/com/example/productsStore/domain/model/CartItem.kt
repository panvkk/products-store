package com.example.productsStore.domain.model

data class CartItem(
    val product: Product,
    val isNotificationsOn: Boolean,
    val quantity: Int
)