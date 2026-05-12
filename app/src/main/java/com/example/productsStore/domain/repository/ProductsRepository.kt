package com.example.productsStore.domain.repository

import com.example.productsStore.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts() : List<Product>
}