package com.example.productsStore.domain.usecase.cart

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class GetCartedProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(id: Int): Resource<CartItem> {
        return cartRepository.getCartedProduct(id)
    }
}