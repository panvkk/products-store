package com.example.productsStore.presentation.update

import com.example.productsStore.presentation.contract.CartCommand
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartNews
import com.example.productsStore.presentation.contract.CartState
import ru.tinkoff.kotea.core.Next
import ru.tinkoff.kotea.core.Update

class CartUpdate : Update<CartState, CartEvent, CartCommand, CartNews> {
    override fun update(
        state: CartState,
        event: CartEvent
    ): Next<CartState, CartCommand, CartNews> =
        when(event) {
            CartEvent.Ui.OnClearCart ->
                Next(state = state, commands = listOf(CartCommand.ClearCart))
            CartEvent.Ui.OnLoadCart ->
                Next(state = state, commands = listOf(CartCommand.LoadCart))
            is CartEvent.Ui.OnNavigateToDetails ->
                Next(state = state, news = listOf(CartNews.NavigateToDetails(event.productId)))
            is CartEvent.Internal.CartLoaded ->
                Next(state = event.newState)
            CartEvent.Internal.CartCleared ->
                Next(state = CartState.Loading, commands = listOf(CartCommand.LoadCart), news = listOf(CartNews.ShowCartClearedToast))
        }
}