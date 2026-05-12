package com.example.productsStore.presentation.model

sealed interface ProductsListUiState {
    data object Loading : ProductsListUiState
    data class Content(val products: List<ProductUiModel>) : ProductsListUiState
    data class Error(val errorMessage: String) : ProductsListUiState
}