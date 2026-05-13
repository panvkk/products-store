package com.example.productsStore.presentation.model

sealed interface ProductDetailsUiModel {
    data object Loading : ProductDetailsUiModel
    data class Content(
        val productTitle: String,
        val description: String,
        val rating: Float,
        val priceInUSD: Float,
        val weight: Int,
        val availabilityStatus: String,
        val warrantyInformation: String
    ) : ProductDetailsUiModel
}