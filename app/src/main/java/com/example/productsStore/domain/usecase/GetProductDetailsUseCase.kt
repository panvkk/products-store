package com.example.productsStore.domain.usecase

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.ProductDetails
import com.example.productsStore.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    private val selectedFields = listOf("title", "description", "rating", "price", "weight", "availabilityStatus", "warrantyInformation").joinToString(",")

    suspend operator fun invoke(id: Int) : Resource<ProductDetails> {
        return repository.getProductDetails(id, selectedFields)
    }
}