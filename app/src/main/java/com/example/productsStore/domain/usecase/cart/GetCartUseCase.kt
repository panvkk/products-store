package com.example.productsStore.domain.usecase.cart

import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() : Flow<List<CartItem>> {
        return cartRepository.getCartedProducts()
    }
}