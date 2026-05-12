package com.example.productsStore.presentation.di.subcomponent

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel

class ProductsListSubcomponent(
    private val dependencies: Dependencies
) {
    fun createProductsListViewModelFactory() : ViewModelProvider.Factory {
        return viewModelFactory {
            initializer { createProductsListViewModel() }
        }
    }
    private fun createProductsListViewModel() : ProductsListViewModel {
        return ProductsListViewModel(dependencies.getProductsUseCase())
    }
    interface Dependencies {
        fun getProductsUseCase() : GetProductsUseCase
    }
}