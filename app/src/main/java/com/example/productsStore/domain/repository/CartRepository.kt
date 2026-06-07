package com.example.productsStore.domain.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.CartedProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartedProducts() : Flow<List<CartedProduct>>
    suspend fun getCartedProduct(id: Int) : Resource<CartedProduct>
    suspend fun addToCart(id: Int, title: String)
    suspend fun updateIsNotificationsOn(id: Int, isNotificationsOn: Boolean)
    suspend fun clearCart()
}