package com.example.productsStore.data.service

import com.example.productsStore.data.dto.ProductDetailsApi
import com.example.productsStore.data.dto.ProductsResponseApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {
    @GET("/products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
        @Query("select") fields: String
    ) : ProductsResponseApi

    @GET("/products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int,
        @Query("select") fields: String
    ) : ProductDetailsApi


    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}