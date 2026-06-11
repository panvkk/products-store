package com.example.productsStore.presentation.update

import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListCommand.AddToCart
import com.example.productsStore.presentation.contract.ProductsListCommand.FetchNextPage
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListNews.NavigateToDetails
import com.example.productsStore.presentation.contract.ProductsListNews.ShowAddedToCartToast
import com.example.productsStore.presentation.contract.ProductsListState
import ru.tinkoff.kotea.core.Next
import ru.tinkoff.kotea.core.Update

class ProductsListUpdate : Update<ProductsListState, ProductsListEvent, ProductsListCommand, ProductsListNews> {
    override fun update(
        state: ProductsListState,
        event: ProductsListEvent
    ): Next<ProductsListState, ProductsListCommand, ProductsListNews> =
        when(event) {
            is ProductsListEvent.Ui.OnSetupPageSize -> {
                val newState = state.copy(pageSize = event.pageSize)
                Next(
                    state = newState,
                    commands = listOf(
                        FetchNextPage(
                            skip = newState.products.size,
                            limit = newState.pageSize
                        )
                    )
                )
            }
            is ProductsListEvent.Ui.OnLoadNextPage -> {
                val shouldFetchNextPage = !state.isLoadingGoing && !state.isLastPageReached
                Next(
                    state = state,
                    commands = if (shouldFetchNextPage) listOf(
                        FetchNextPage(
                            skip = state.products.size,
                            limit = state.pageSize
                        )
                    )
                    else emptyList()
                )
            }
            is ProductsListEvent.Ui.OnAddToCart ->
                Next(state = state, commands = listOf(AddToCart(event.productUiModel)))
            is ProductsListEvent.Ui.OnNavigateDetails ->
                Next(state = state, news = listOf(NavigateToDetails(event.productId)))
            is ProductsListEvent.Internal.NextPageLoaded ->
                Next(
                    state = state.copy(
                        products = state.products + event.newProducts,
                        isLoadingGoing = false,
                        isLastPageReached = event.isLastPage,
                        error = event.error
                    )
                )
            is ProductsListEvent.Internal.LoadingStarted ->
                Next(state = state.copy(isLoadingGoing = true))
            is ProductsListEvent.Internal.AddedToCart ->
                Next(state = state, news = listOf(ShowAddedToCartToast(event.addedProductTitle)))
        }
}