package com.example.productsStore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carted_products_table")
data class CartedProductEntity(
    @PrimaryKey @ColumnInfo("product_id") val productId: Int,
    @ColumnInfo("product_title") val productTitle: String,
    @ColumnInfo("is_notifications_on") val isNotificationsOn: Boolean,
    @ColumnInfo("quantity") val quantity: Int
)
