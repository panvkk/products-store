package com.example.productsStore.presentation.model

import android.net.Uri

data class ProductDetailsUiModel(
    val productTitle: String,
    val description: String,
    val mainImageUri: Uri?,
    val rating: Float,
    val priceInUSD: Float,
    val weight: Int,
    val availabilityStatus: String,
    val warrantyInformation: String,
    val isExpiredInfo: Boolean
)