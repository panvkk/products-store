package com.example.productsStore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.productsStore.data.local.entity.ProductDetailsEntity

@Dao
interface ProductDetailsCacheDao {
    @Query("SELECT * FROM product_details_cache_table WHERE id = :id")
    suspend fun getDetails(id: Int) : ProductDetailsEntity?
    @Insert(onConflict = REPLACE)
    suspend fun putDetails(productDetailsEntity: ProductDetailsEntity)
}