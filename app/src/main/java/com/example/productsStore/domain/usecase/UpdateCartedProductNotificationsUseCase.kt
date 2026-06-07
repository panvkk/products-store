package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartedProductNotificationsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int, isNotificationsOn: Boolean) {
        repository.updateIsNotificationsOn(productId, isNotificationsOn)
    }
}