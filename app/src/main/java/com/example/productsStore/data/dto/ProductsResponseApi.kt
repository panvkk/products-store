package com.example.productsStore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseApi(
    @SerialName("products")
    val productsApi: List<ProductApi>
)
