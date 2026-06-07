package com.example.productsStore.domain.model

data class CartedProduct(
    val id: Int,
    val isNotificationsOn: Boolean,
    val quantity: Int
)