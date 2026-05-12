package com.example.productsStore.data.di

import com.example.productsStore.data.ProductsRepositoryImpl
import com.example.productsStore.data.service.ProductsService

class DataComponent(
    private val dependencies : Dependencies
) {
    val productsRepository by lazy {
        ProductsRepositoryImpl(dependencies.getProductsService())
    }
    interface Dependencies {
        fun getProductsService() : ProductsService
    }
}