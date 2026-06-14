package com.example.productsStore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.productsStore.data.local.entity.CartedProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCartDao {
    @Query("SELECT * FROM carted_products_table")
    fun getCartedProducts() : Flow<List<CartedProductEntity>>

    @Query("SELECT * FROM carted_products_table WHERE product_id = :id")
    suspend fun getCartedProduct(id: Int) : CartedProductEntity?

    @Query("SELECT quantity FROM carted_products_table WHERE product_id = :id")
    suspend fun getProductQuantityInCart(id: Int) : Int?

    @Insert(onConflict = REPLACE)
    suspend fun putProduct(cartedProductEntity: CartedProductEntity)

    @Query("DELETE FROM carted_products_table")
    suspend fun clearCart()
}