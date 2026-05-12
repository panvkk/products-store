package com.example.productsStore.presentation.mapper

import com.example.productsStore.domain.model.Product
import com.example.productsStore.presentation.model.ProductUiModel

fun Product.toUiModel() = ProductUiModel(id, productTitle, price, brand)