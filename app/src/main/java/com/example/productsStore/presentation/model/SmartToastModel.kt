package com.example.productsStore.presentation.model

data class SmartToastModel(
    val message: String,
    val duration: Long = 3_000L,
    val onClick: (() -> Unit)? = null
)