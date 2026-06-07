package com.example.productsStore.domain.usecase

import com.example.productsStore.core.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveAllNotificationsUseCase @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateAlarmManagerUseCase: UpdateAlarmManagerUseCase
) {
    suspend operator fun invoke() {
        val cartItems = (getCartUseCase.invoke().first() as? Resource.Success)?.data ?: return
        cartItems.forEach { cartItem ->
            updateAlarmManagerUseCase(cartItem.product.id, false)
        }
    }
}