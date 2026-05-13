package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.ProductsRepository

class GetProductsUseCase(
    private val repository: ProductsRepository
) {
    private val selectedFields = listOf("id", "title", "price", "brand").joinToString(",")

    suspend operator fun invoke() : List<Product> {
        return repository.getProducts(selectedFields)
    }
}