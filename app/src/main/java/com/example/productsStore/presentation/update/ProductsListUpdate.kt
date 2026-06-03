package com.example.productsStore.presentation.update

import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
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
                    commands = listOf(ProductsListCommand.FetchNextPage(newState))
                )
            }
            is ProductsListEvent.Ui.OnLoadNextPage -> {
                Next(
                    state = state,
                    commands = if (!state.isLoadingGoing && !state.isLastPageReached)
                        listOf(ProductsListCommand.FetchNextPage(state)) else emptyList()
                )
            }
            is ProductsListEvent.Ui.OnAddToCart ->
                Next(state = state, commands = listOf(ProductsListCommand.AddToCart(event.productId)))
            is ProductsListEvent.Internal.NextPageLoaded ->
                Next(state = event.newState)
            is ProductsListEvent.Internal.LoadingStarted ->
                Next(state = state.copy(isLoadingGoing = true))
        }
}