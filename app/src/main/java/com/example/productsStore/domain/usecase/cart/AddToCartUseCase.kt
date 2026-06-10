package com.example.productsStore.domain.usecase.cart

import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.addToCart(product)
    }
}