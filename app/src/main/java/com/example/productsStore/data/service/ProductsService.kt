package com.example.productsStore.data.service

import com.example.productsStore.data.dto.ProductsResponseApi
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("/products")
    suspend fun getProducts(
        @Query("select") fields: String
    ) : ProductsResponseApi

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}