package com.example.productsStore.domain.repository

import com.example.productsStore.domain.model.CartedProduct
import com.example.productsStore.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartedProducts() : Flow<List<CartedProduct>>
    suspend fun addToCart(id: Int)
    suspend fun clearCart()
}