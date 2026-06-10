package com.example.productsStore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.productsStore.data.local.dao.ProductCartDao
import com.example.productsStore.data.local.dao.ProductDetailsCacheDao
import com.example.productsStore.data.local.entity.CartedProductEntity
import com.example.productsStore.data.local.entity.ProductDetailsEntity

@Database(
    entities = [ProductDetailsEntity::class, CartedProductEntity::class],
    version = 5,
    autoMigrations = []
) abstract class ProductsStoreDatabase : RoomDatabase() {
    abstract fun productDetailsCacheDao() : ProductDetailsCacheDao
    abstract fun productCartDao() : ProductCartDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE product_details_cache_table ADD COLUMN image_url TEXT DEFAULT NULL")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `carted_products_table` (
                        `product_id` INTEGER NOT NULL, 
                        `quantity` INTEGER NOT NULL, 
                        PRIMARY KEY(`product_id`)
                    )
                """)
            }
        }
    }
}