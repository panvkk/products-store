package com.example.productsStore.presentation.contract

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.CartItemUiModel

sealed interface CartState {
    data object Loading : CartState
    data class Error(val error: DomainError) : CartState
    data class Content(
        val cartItems: List<CartItemUiModel>,
        val cartSize: Int
    ) : CartState
}

sealed interface CartEvent {
    sealed interface Ui : CartEvent {
        data object OnClearCart : Ui
        data object OnLoadCart : Ui
        data class OnNavigateToDetails(val productId: Int) : Ui
    }
    sealed interface Internal : CartEvent {
        data class CartLoaded(val newState: CartState) : Internal
        data object CartCleared : Internal
    }
}

sealed interface CartCommand {
    data object ClearCart : CartCommand
    data object LoadCart : CartCommand
}

sealed interface CartNews {
    data class NavigateToDetails(val productId: Int) : CartNews
    data object ShowCartClearedToast : CartNews
}