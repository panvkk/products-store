package com.example.productsStore.presentation.model

import com.example.productsStore.core.domain.DomainError

sealed interface CartUiState {
    data object Loading : CartUiState
    data class Error(val error: DomainError) : CartUiState
    data class Content(val cartItems: List<CartItemUiModel>) : CartUiState
}