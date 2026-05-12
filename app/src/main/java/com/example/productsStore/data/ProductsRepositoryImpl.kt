package com.example.productsStore.data

import com.example.productsStore.data.service.ProductsService
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val productsService: ProductsService
) : ProductsRepository {
    override suspend fun getProducts(): List<Product> {
        return productsService.getProducts().items.map { it.toDomain() }
    }
}