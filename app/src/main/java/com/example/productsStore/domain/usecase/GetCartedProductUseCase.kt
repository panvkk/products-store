package com.example.productsStore.domain.usecase

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.CartedProduct
import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class GetCartedProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(id: Int): Resource<CartedProduct> {
        return cartRepository.getCartedProduct(id)
    }
}