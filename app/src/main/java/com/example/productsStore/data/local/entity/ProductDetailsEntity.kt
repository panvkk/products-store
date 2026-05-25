package com.example.productsStore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock.System

@Entity(tableName = "product_details_cache_table")
data class ProductDetailsEntity(
    @PrimaryKey(false) val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val price: Float,
    val weight: Int,
    @ColumnInfo("availability_status")
    val availabilityStatus: String,
    @ColumnInfo("warranty_information")
    val warrantyInformation: String,
    val timestamp: Long = System.now().epochSeconds
)