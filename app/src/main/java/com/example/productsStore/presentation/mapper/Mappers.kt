package com.example.productsStore.presentation.mapper

import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.model.ProductDetails
import com.example.productsStore.presentation.model.ProductDetailsUiModel
import com.example.productsStore.presentation.model.ProductUiModel

fun Product.toUiModel() = ProductUiModel(id, productTitle, price, brand)

fun ProductDetails.toUiModel() = ProductDetailsUiModel.Content(title, description, rating, price, weight, availabilityStatus, warrantyInformation)