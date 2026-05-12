package com.example.productsStore.data.api

import com.example.productsStore.data.dto.ProductsResponseApi
import retrofit2.http.GET

interface ProductsService {
    @GET("/products")
    suspend fun getProducts() : ProductsResponseApi

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}