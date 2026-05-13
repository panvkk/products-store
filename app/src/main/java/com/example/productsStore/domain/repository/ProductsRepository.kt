package com.example.productsStore.domain.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts(skip: Int, limit: Int, fields: String) : Resource<List<Product>>
}