package com.example.productsStore.presentation.contract

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.ProductUiModel

data class ProductsListState(
    val isLoadingGoing: Boolean,
    val isLastPageReached: Boolean,
    val products: List<ProductUiModel>,
    val pageSize: Int,
    val error: DomainError? = null
)

sealed interface ProductsListEvent {
    sealed interface Ui : ProductsListEvent {
        data class OnSetupPageSize(val pageSize: Int) : Ui
        data class OnAddToCart(val productId: Int) : Ui
        data object OnLoadNextPage : Ui
        data class OnNavigateDetails(val productId: Int) : Ui
    }
    sealed interface Internal : ProductsListEvent {
        data object AddedToCart : Internal
        data class NextPageLoaded(val newState: ProductsListState) : Internal
        data object LoadingStarted : Internal
    }
}

sealed interface ProductsListCommand {
    data class FetchNextPage(val state: ProductsListState) : ProductsListCommand
    data class AddToCart(val productId: Int) : ProductsListCommand
}

sealed interface ProductsListNews {
    data class NavigateToDetails(val productId: Int) : ProductsListNews
    data object ShowAddedToCartToast : ProductsListNews
}