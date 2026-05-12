package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.model.ProductUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsListViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _products = MutableStateFlow(emptyList<ProductUiModel>())
    val products = _products.asStateFlow()
}