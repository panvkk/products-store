package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductsListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsListViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsListUiState>(ProductsListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value =
                ProductsListUiState.Content(
                    getProductsUseCase.invoke().map { it.toUiModel() }
                )
        }
    }
}