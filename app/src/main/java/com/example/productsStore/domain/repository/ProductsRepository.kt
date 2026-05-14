package com.example.productsStore.domain.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.model.ProductDetails

interface ProductsRepository {
    suspend fun getProducts(skip: Int, limit: Int, fields: String) : Resource<List<Product>>
    suspend fun getProductDetails(id: Int, fields: String) : Resource<ProductDetails>
}