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
        data class OnAddToCart(val productUiModel: ProductUiModel) : Ui
        data object OnLoadNextPage : Ui
        data class OnNavigateDetails(val productId: Int) : Ui
    }
    sealed interface Internal : ProductsListEvent {
        data object AddedToCart : Internal
        data class NextPageLoaded(
            val newProducts: List<ProductUiModel>,
            val isLastPage: Boolean,
            val error: DomainError?
        ) : Internal
        data object LoadingStarted : Internal
    }
}

sealed interface ProductsListCommand {
    data class FetchNextPage(val skip: Int, val limit: Int) : ProductsListCommand
    data class AddToCart(val productUiModel: ProductUiModel) : ProductsListCommand
}

sealed interface ProductsListNews {
    data class NavigateToDetails(val productId: Int) : ProductsListNews
    data object ShowAddedToCartToast : ProductsListNews
}