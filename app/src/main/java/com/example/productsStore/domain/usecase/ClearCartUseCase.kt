package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repository: CartRepository,
    private val removeAllNotificationsUseCase: RemoveAllNotificationsUseCase
) {
    suspend operator fun invoke() {
        removeAllNotificationsUseCase.invoke()
        repository.clearCart()
    }
}