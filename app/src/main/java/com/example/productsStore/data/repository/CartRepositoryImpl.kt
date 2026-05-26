package com.example.productsStore.data.repository

import com.example.productsStore.core.logger.LoggingProvider
import com.example.productsStore.data.local.dao.ProductCartDao
import com.example.productsStore.data.local.entity.CartedProductEntity
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.CartedProduct
import com.example.productsStore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val productCartDao: ProductCartDao,
    private val logger: LoggingProvider
) : CartRepository {

    override fun getCartedProducts() : Flow<List<CartedProduct>>{
        return productCartDao.getCartedProducts()
            .map { cartedProductEntities -> cartedProductEntities.map { it.toDomain() } }
    }

    override suspend fun addToCart(id: Int) {
        val currentQuantityInCart = productCartDao.getProductQuantityInCart(id) ?: 0
        productCartDao.putProduct(
            CartedProductEntity(id, currentQuantityInCart + 1)
        )
    }

    override suspend fun clearCart() {
        productCartDao.clearCart()
    }
}