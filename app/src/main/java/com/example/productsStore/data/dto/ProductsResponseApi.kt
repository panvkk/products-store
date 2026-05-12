package com.example.productsStore.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseApi(
    val items: List<ProductApi>
)
