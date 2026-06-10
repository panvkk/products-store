package com.example.productsStore.presentation.update

import com.example.productsStore.presentation.contract.CartCommand
import com.example.productsStore.presentation.contract.CartCommand.*
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartNews
import com.example.productsStore.presentation.contract.CartNews.*
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
            is CartEvent.Ui.OnNavigateToDetails ->
                Next(state = state, news = listOf(NavigateToDetails(event.productId)))
            is CartEvent.Internal.CartLoaded ->
                Next(state = event.newState)
            CartEvent.Internal.CartCleared ->
                Next(state = CartState.Loading, news = listOf(ShowCartClearedToast))
            is CartEvent.Ui.OnUpdateNotifications ->
                Next(state = state, commands = listOf(UpdateNotifications(event.productId, event.isNotificationsOn)))
            CartEvent.Internal.NotificationsUpdated ->
                Next(state = state)
            CartEvent.Internal.DisplayCartHint ->
                Next(state = state, news = listOf(ShowCartHintDialog))
        }
}