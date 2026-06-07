package com.example.productsStore.domain.usecase

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val getProductByIdUseCase: GetProductByIdUseCase
) {
    operator fun invoke() : Flow<Resource<List<CartItem>>> {
        return cartRepository.getCartedProducts().map { cartedProducts ->
            val cartItems = mutableListOf<CartItem>()
            cartedProducts.forEach {
                val resourceProduct = getProductByIdUseCase(it.id)  // TODO переделать, чтобы вначале эмитился какой-нибудь Loading
                if(resourceProduct is Resource.Success)
                    cartItems.add(CartItem(resourceProduct.data, it.isNotificationsOn, it.quantity))
                else if(resourceProduct is Resource.Error)
                    Resource.Error(resourceProduct.error)
            }
            Resource.Success(cartItems.toList())
        }
    }
}