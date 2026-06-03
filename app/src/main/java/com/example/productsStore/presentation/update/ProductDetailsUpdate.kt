package com.example.productsStore.presentation.update

import com.example.productsStore.presentation.contract.ProductDetailsCommand
import com.example.productsStore.presentation.contract.ProductDetailsCommand.LoadProductDetails
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.contract.ProductDetailsNews
import com.example.productsStore.presentation.contract.ProductDetailsState
import ru.tinkoff.kotea.core.Next
import ru.tinkoff.kotea.core.Update

class ProductDetailsUpdate : Update<ProductDetailsState, ProductDetailsEvent, ProductDetailsCommand, ProductDetailsNews> {
    override fun update(
        state: ProductDetailsState,
        event: ProductDetailsEvent
    ): Next<ProductDetailsState, ProductDetailsCommand, ProductDetailsNews> =
        when(event) {
            is ProductDetailsEvent.Ui.SetProductId ->
                Next(
                    state = state.copy(productId = event.productId),
                    commands = listOf(LoadProductDetails(event.productId))
                )
            is ProductDetailsEvent.Ui.RefreshDetails ->
                Next(
                    commands = if (event.productId != null)
                        listOf(LoadProductDetails(event.productId))
                    else emptyList()
                )
            is ProductDetailsEvent.Internal.DetailsLoaded ->
                Next(state = state.copy(screenState = event.newScreenState))
        }
}