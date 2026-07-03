package com.example.productsStore.presentation.model

import com.example.productsStore.core.SMART_TOAST_DURATION

data class SmartToastModel(
    val message: String,
    val duration: Long = SMART_TOAST_DURATION,
    val onClick: (() -> Unit)? = null
)