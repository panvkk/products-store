package com.example.productsStore.testing.stub

import com.example.productsStore.data.local.dao.ProductDetailsCacheDao
import com.example.productsStore.data.local.entity.ProductDetailsEntity

internal class ProductDetailsCacheDaoStub : ProductDetailsCacheDao {
    var productDetailsToBeReturned: ProductDetailsEntity? = null
    var savedProductDetails: ProductDetailsEntity? = null

    override suspend fun getDetails(id: Int): ProductDetailsEntity? = productDetailsToBeReturned

    override suspend fun putDetails(productDetailsEntity: ProductDetailsEntity) {
        savedProductDetails = productDetailsEntity
    }
}