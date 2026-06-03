package com.example.productsStore.presentation.contract

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.ProductDetailsUiModel

data class ProductDetailsState(
    val productId: Int?,
    val screenState: ProductDetailsScreenState
)

sealed interface ProductDetailsScreenState {
    data object Loading : ProductDetailsScreenState
    data class Error(val error: DomainError) : ProductDetailsScreenState
    data class Content(val productDetails: ProductDetailsUiModel) : ProductDetailsScreenState
}

sealed interface ProductDetailsEvent {
    sealed interface Ui : ProductDetailsEvent {
        data class RefreshDetails(val productId: Int?) : Ui
        data class SetProductId(val productId: Int) : Ui
    }
    sealed interface Internal : ProductDetailsEvent {
        data class DetailsLoaded(val newScreenState: ProductDetailsScreenState) : Internal
    }
}

sealed interface ProductDetailsCommand {
    data class LoadProductDetails(val productId: Int) : ProductDetailsCommand
}

sealed interface ProductDetailsNews {

}