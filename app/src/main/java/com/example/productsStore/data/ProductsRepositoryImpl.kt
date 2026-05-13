package com.example.productsStore.data

import com.example.productsStore.data.service.ProductsService
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductsRepository {
    override suspend fun getProducts(fields: String): List<Product> {
        return productsService.getProducts(fields).productsApi.map { it.toDomain() }
    }
}