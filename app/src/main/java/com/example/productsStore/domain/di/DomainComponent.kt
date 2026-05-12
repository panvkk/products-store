package com.example.productsStore.domain.di

import com.example.productsStore.domain.repository.ProductsRepository
import com.example.productsStore.domain.usecase.GetProductsUseCase

class DomainComponent(
    private val dependencies : Dependencies
) {
    fun getProductsUseCase() : GetProductsUseCase {
        return GetProductsUseCase(dependencies.getRepository())
    }
    interface Dependencies {
        fun getRepository() : ProductsRepository
    }
}