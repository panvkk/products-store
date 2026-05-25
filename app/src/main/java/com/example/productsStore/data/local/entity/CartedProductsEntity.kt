package com.example.productsStore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carted_products_table")
data class CartedProductsEntity(
    @PrimaryKey(false)
    @ColumnInfo("product_id")
    val productId: Int
)
