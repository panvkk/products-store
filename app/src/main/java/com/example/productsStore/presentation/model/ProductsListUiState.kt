package com.example.productsStore.presentation.model

import com.example.productsStore.core.domain.DomainError

sealed interface ProductsListUiState {
    data object Loading : ProductsListUiState
    data class Content(val products: List<ProductUiModel>) : ProductsListUiState
    data class Error(val error: DomainError) : ProductsListUiState
}