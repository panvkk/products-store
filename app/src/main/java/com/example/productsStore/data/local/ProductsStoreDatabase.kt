package com.example.productsStore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.productsStore.data.local.dao.ProductDetailsCacheDao
import com.example.productsStore.data.local.entity.ProductDetailsEntity

@Database(entities = [ProductDetailsEntity::class], version = 2)
abstract class ProductsStoreDatabase : RoomDatabase() {
    abstract fun productDetailsCacheDao() : ProductDetailsCacheDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE product_details_cache_table ADD COLUMN image_url TEXT DEFAULT NULL")
            }
        }
    }
}