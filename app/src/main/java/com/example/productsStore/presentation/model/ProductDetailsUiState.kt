package com.example.productsStore.presentation.model

import com.example.productsStore.core.domain.DomainError

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState
    data class Error(val error: DomainError) : ProductDetailsUiState
    data class Content(val productDetails: ProductDetailsUiModel) : ProductDetailsUiState
}