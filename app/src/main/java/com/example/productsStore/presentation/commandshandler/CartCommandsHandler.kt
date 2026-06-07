package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.ClearCartUseCase
import com.example.productsStore.domain.usecase.GetCartUseCase
import com.example.productsStore.domain.usecase.UpdateCartedProductNotificationsUseCase
import com.example.productsStore.presentation.contract.CartCommand
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartEvent.Internal.*
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.mapper.toUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class CartCommandsHandler @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val updateCartedProductNotificationsUseCase: UpdateCartedProductNotificationsUseCase,
    @ApplicationScope
    private val applicationScope: CoroutineScope
) : CommandsFlowHandler<CartCommand, CartEvent> {
    override fun handle(commands: Flow<CartCommand>): Flow<CartEvent> = flow {
        commands.collect { command ->
            when(command) {
                CartCommand.ClearCart -> {
                    clearCart()
                    emit(CartEvent.Internal.CartCleared)
                }
                CartCommand.LoadCart -> {
                    val newState = loadCart()
                    emit(CartLoaded(newState))
                }
                is CartCommand.UpdateNotifications -> {
                    updateNotifications(command.productId, command.isNotificationsOn)
                    emit(NotificationsUpdated)
                }
            }
        }
    }

    private fun updateNotifications(productId: Int, isNotificationsOn: Boolean) {
        applicationScope.launch {
            updateCartedProductNotificationsUseCase.invoke(productId, isNotificationsOn)
        }
    }

    private suspend fun loadCart() : CartState =
        getCartUseCase.invoke().map { resource ->
            when(resource) {
                is Resource.Success -> {
                    val cartItems = resource.data.map { it.toUiModel() }
                    var cartSize = 0
                    cartItems.forEach { cartSize += it.quantity }
                    CartState.Content(cartItems, cartSize)
                }
                is Resource.Error -> {
                    CartState.Error(resource.error)
                }
            }
        }.first()

    private fun clearCart() {
        applicationScope.launch {
            clearCartUseCase()
        }
    }
}