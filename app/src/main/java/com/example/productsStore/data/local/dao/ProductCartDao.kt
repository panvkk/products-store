package com.example.productsStore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.productsStore.data.local.entity.CartedProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCartDao {
    @Query("SELECT * FROM carted_products_table")
    fun getCartedProducts() : Flow<CartedProductsEntity>

    @Insert(onConflict = IGNORE)
    suspend fun addToCart(cartedProductsEntity: CartedProductsEntity)

    @Query("DELETE FROM carted_products_table")
    suspend fun clearCart()

    @Query("SELECT EXISTS(SELECT 1 FROM carted_products_table WHERE product_id = :productId)")
    suspend fun isCarted(productId: Int) : Boolean
}