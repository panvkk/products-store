package com.example.productsStore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productsStore.data.local.dao.ProductDetailsCacheDao
import com.example.productsStore.data.local.entity.ProductDetailsEntity

@Database(entities = [ProductDetailsEntity::class], version = 1)
abstract class ProductsStoreDatabase : RoomDatabase() {
    abstract fun productDetailsCacheDao() : ProductDetailsCacheDao
}