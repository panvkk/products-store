package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.usecase.ClearCartUseCase
import com.example.productsStore.domain.usecase.GetCartUseCase
import com.example.productsStore.domain.usecase.UpdateNotificationStateUseCase
import com.example.productsStore.presentation.contract.CartCommand
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartEvent.Internal.CartLoaded
import com.example.productsStore.presentation.contract.CartEvent.Internal.NotificationsUpdated
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.mapper.toUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class CartCommandsHandler @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val updateNotificationStateUseCase: UpdateNotificationStateUseCase,
    @ApplicationScope
    private val applicationScope: CoroutineScope
) : CommandsFlowHandler<CartCommand, CartEvent> {
    override fun handle(commands: Flow<CartCommand>): Flow<CartEvent> {
        return commands.flatMapMerge { command ->
            flow {
                when (command) {
                    CartCommand.ClearCart -> {
                        clearCart()
                        emit(CartEvent.Internal.CartCleared)
                    }
                    CartCommand.LoadCart -> {
                        getCartUseCase.invoke().collect { resource ->
                            val newState = foldCartItems(resource)
                            emit(CartLoaded(newState))
                        }
                    }
                    is CartCommand.UpdateNotifications -> {
                        updateNotifications(command.productId, command.isNotificationsOn)
                        emit(NotificationsUpdated)
                    }
                }
            }
        }
    }

    private fun updateNotifications(productId: Int, isNotificationsOn: Boolean) {
        applicationScope.launch {
            updateNotificationStateUseCase.invoke(productId, isNotificationsOn)
        }
    }

    private fun foldCartItems(resource: Resource<List<CartItem>>) : CartState =
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

    private fun clearCart() {
        applicationScope.launch {
            clearCartUseCase()
        }
    }
}