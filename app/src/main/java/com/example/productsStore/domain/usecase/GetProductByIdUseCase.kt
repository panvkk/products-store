package com.example.productsStore.domain.usecase

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    private val selectedFields = listOf("id", "title", "price", "brand").joinToString(",")

    suspend operator fun invoke(id: Int) : Resource<Product> {
        return repository.getProductById(id, selectedFields)
    }
}