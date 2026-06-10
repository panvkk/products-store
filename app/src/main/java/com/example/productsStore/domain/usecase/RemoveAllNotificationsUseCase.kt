package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.usecase.cart.GetCartUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveAllNotificationsUseCase @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateAlarmManagerUseCase: UpdateAlarmManagerUseCase
) {
    suspend operator fun invoke() {
        val cartItems = getCartUseCase.invoke().first()
        cartItems.forEach { cartItem ->
            updateAlarmManagerUseCase(cartItem.product.id, false)
        }
    }
}