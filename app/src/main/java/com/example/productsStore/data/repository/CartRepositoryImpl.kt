package com.example.productsStore.data.repository

import com.example.productsStore.core.logger.Logger
import com.example.productsStore.data.local.dao.ProductCartDao
import com.example.productsStore.data.local.entity.CartedProductEntity
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.CartedProduct
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

    override fun getCartedProducts() : Flow<List<CartedProduct>>{
        return productCartDao.getCartedProducts()
            .map { cartedProductEntities -> cartedProductEntities.map { it.toDomain() } }
            .catch { e ->
                logger.e(TAG, e.message ?: UNKNOWN_ERROR)
            }
    }

    override suspend fun addToCart(id: Int, title: String) {
        try {
            val currentStateInCart = productCartDao.getCartedProduct(id)

            val quantity = currentStateInCart?.quantity?.inc() ?: 1
            val isNotificationOn = currentStateInCart?.isNotificationsOn ?: false
            productCartDao.putProduct(
                CartedProductEntity(
                    productId = id,
                    productTitle = title,
                    isNotificationsOn = isNotificationOn,
                    quantity = quantity
                )
            )
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