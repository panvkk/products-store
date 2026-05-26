package com.example.productsStore.data.mapper

import com.example.productsStore.data.dto.ProductApi
import com.example.productsStore.data.dto.ProductDetailsApi
import com.example.productsStore.data.local.entity.CartedProductEntity
import com.example.productsStore.data.local.entity.ProductDetailsEntity
import com.example.productsStore.domain.model.CartedProduct
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.model.ProductDetails

fun ProductApi.toDomain() = Product(id, title, price, brand)
fun ProductDetailsApi.toDomain() = ProductDetails(title, description, imageUrls.firstOrNull(), rating, price, weight, availabilityStatus, warrantyInformation)
fun ProductDetailsApi.toEntity(id: Int) = ProductDetailsEntity(id, title, description, imageUrls.firstOrNull(), rating, price, weight, availabilityStatus, warrantyInformation)
fun ProductDetailsEntity.toDomain(isIrrelevantInfo: Boolean) = ProductDetails(title, description, imageUrl, rating, price, weight, availabilityStatus, warrantyInformation, isIrrelevantInfo)
fun CartedProductEntity.toDomain() = CartedProduct(productId, quantity)