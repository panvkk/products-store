package com.example.productsStore.presentation.di

import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.di.subcomponent.ProductsListSubcomponent

class PresentationComponent(
    private val dependencies: Dependencies
) {
    fun createProductsListSubcomponent() =
        ProductsListSubcomponent(object : ProductsListSubcomponent.Dependencies {
            override fun getProductsUseCase(): GetProductsUseCase =
                dependencies.getProductsUseCase()
        })


    interface Dependencies {
        fun getProductsUseCase() : GetProductsUseCase
    }
}