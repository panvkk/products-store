package com.example.productsStore.presentation.model

data class ProductsListUiState(
    val isLoadingGoing: Boolean,
    val isLastPageReached: Boolean,
    val products: List<ProductUiModel>,
    val errorMessage: String? = null
)