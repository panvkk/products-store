package com.example.productsStore.data.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.core.logger.Logger
import com.example.productsStore.data.local.dao.ProductCartDao
import com.example.productsStore.data.mapper.toCartEntity
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.CartRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val productCartDao: ProductCartDao,
    private val logger: Logger
) : CartRepository {

    override fun getCartedProducts() : Flow<List<CartItem>> {
        return productCartDao.getCartedProducts()
            .map { cartedProductEntities -> cartedProductEntities.map { it.toDomain() } }
            .catch { e ->
                logger.e(TAG, e.message ?: UNKNOWN_ERROR)
            }
    }

    override suspend fun getCartedProduct(id: Int): Resource<CartItem> {
        return try {
            val cartedProduct =
                productCartDao.getCartedProduct(id) ?: return Resource.Error(DomainError.Other)
            Resource.Success(cartedProduct.toDomain())
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            logger.e(TAG, e.message ?: UNKNOWN_ERROR)
            Resource.Error(DomainError.Other)
        }
    }

    override suspend fun addToCart(product: Product) {
        try {
            val currentStateInCart = productCartDao.getCartedProduct(product.id)

            val quantity = currentStateInCart?.quantity?.inc() ?: 1
            val isNotificationsOn = currentStateInCart?.isNotificationsOn ?: false
            productCartDao.putProduct(product.toCartEntity(quantity, isNotificationsOn))
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            logger.e(TAG, e.message ?: UNKNOWN_ERROR)
        }
    }

    override suspend fun updateIsNotificationsOn(
        id: Int,
        isNotificationsOn: Boolean
    ) {
        try {
            val currentStateInCart = productCartDao.getCartedProduct(id) ?: return
            productCartDao.putProduct(
                currentStateInCart.copy(
                    isNotificationsOn = isNotificationsOn
                )
            )
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            logger.e(TAG, e.message ?: UNKNOWN_ERROR)
        }
    }

    override suspend fun clearCart() {
        try {
            productCartDao.clearCart()
        } catch (e: Exception) {
            if(e is CancellationException) throw e
            logger.e(TAG, e.message ?: UNKNOWN_ERROR)
        }
    }
    companion object {
        private const val TAG = "CartRepositoryImpl"
        private const val UNKNOWN_ERROR = "Unknown error."
    }
}