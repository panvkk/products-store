package com.example.productsStore.data.mapper

import com.example.productsStore.data.dto.ProductApi
import com.example.productsStore.domain.model.Product

fun ProductApi.toDomain() = Product(id, title, price, brand)