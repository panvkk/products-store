package com.example.productsStore.data.di

import android.content.Context
import androidx.room.Room
import com.example.productsStore.data.local.ProductsStoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        context = appContext,
        klass = ProductsStoreDatabase::class.java,
        name = "product-details-db.db"
    )
        .addMigrations(ProductsStoreDatabase.MIGRATION_1_2)
        .build()

    @Provides
    fun provideProductDetailsCacheDao(db: ProductsStoreDatabase) = db.productDetailsCacheDao()
}