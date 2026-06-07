package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(id: Int, title: String) {
        repository.addToCart(id, title)
    }
}