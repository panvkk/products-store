package com.example.productsStore.testing.stub

import com.example.productsStore.data.dto.ProductApi
import com.example.productsStore.data.dto.ProductDetailsApi
import com.example.productsStore.data.dto.ProductsResponseApi
import com.example.productsStore.data.remote.service.ProductsService
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal class ProductsServiceStub : ProductsService {
    var productsListToBeReturned: List<ProductApi> = emptyList()
    var productToBeReturned: ProductApi? = null
    var shouldThrowException: Boolean = false
    var productDetailsToBeReturned: ProductDetailsApi = ProductDetailsApi(
        title = "",
        description = "",
        imageUrls = listOf("firstUrl", "secondUrl"),
        rating = 1f,
        price = 1f,
        weight = 1,
        availabilityStatus = "",
        warrantyInformation = ""
    )
    override suspend fun getProducts(
        skip: Int,
        limit: Int,
        fields: String
    ): ProductsResponseApi =
        if (shouldThrowException) throw IOException()
        else ProductsResponseApi(productsApi = productsListToBeReturned)

    override suspend fun getProductDetails(
        id: Int,
        fields: String
    ): ProductDetailsApi = if(shouldThrowException) throw IOException() else productDetailsToBeReturned

    override suspend fun getProductById(
        id: Int,
        fields: String
    ): ProductApi? = if(shouldThrowException) throw IOException() else productToBeReturned
}