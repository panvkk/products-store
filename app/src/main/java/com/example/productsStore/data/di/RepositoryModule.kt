package com.example.productsStore.data.di

import com.example.productsStore.data.repository.CartRepositoryImpl
import com.example.productsStore.data.repository.ProductsRepositoryImpl
import com.example.productsStore.domain.repository.CartRepository
import com.example.productsStore.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl) : ProductsRepository

    @Singleton
    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl) : CartRepository
}