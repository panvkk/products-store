package com.example.productsStore.presentation.mapper

import androidx.core.net.toUri
import com.example.productsStore.domain.model.CartItem
import com.example.productsStore.domain.model.Product
import com.example.productsStore.domain.model.ProductDetails
import com.example.productsStore.presentation.model.CartItemUiModel
import com.example.productsStore.presentation.model.ProductDetailsUiModel
import com.example.productsStore.presentation.model.ProductUiModel

fun Product.toUiModel() = ProductUiModel(id, productTitle, price, brand)

fun ProductDetails.toUiModel() = ProductDetailsUiModel(title, description, imageUrl?.toUri(), rating, price, weight, availabilityStatus, warrantyInformation, isIrrelevantInfo)
fun CartItem.toUiModel() = CartItemUiModel(product.toUiModel(), isNotificationsOn, quantity)