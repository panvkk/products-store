package com.example.productsStore.data.di

import com.example.productsStore.data.ProductsRepositoryImpl
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
}