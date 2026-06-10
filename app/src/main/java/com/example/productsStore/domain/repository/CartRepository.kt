package com.example.productsStore.domain.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartedProducts() : Flow<List<CartItem>>
    suspend fun getCartedProduct(id: Int) : Resource<CartItem>
    suspend fun addToCart(product: Product)
    suspend fun updateIsNotificationsOn(id: Int, isNotificationsOn: Boolean)
    suspend fun clearCart()
}