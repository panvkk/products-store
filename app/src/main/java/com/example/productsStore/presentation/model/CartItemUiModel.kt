package com.example.productsStore.presentation.model

data class CartItemUiModel(
    val product: ProductUiModel,
    val isNotificationsOn: Boolean,
    val quantity: Int
)
