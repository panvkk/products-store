package com.example.productsStore.presentation.model

import com.example.productsStore.core.domain.DomainError

data class ProductsListUiState(
    val isLoadingGoing: Boolean,
    val isLastPageReached: Boolean,
    val products: List<ProductUiModel>,
    val error: DomainError? = null
)