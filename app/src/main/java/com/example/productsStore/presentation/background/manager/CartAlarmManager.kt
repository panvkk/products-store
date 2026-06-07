package com.example.productsStore.presentation.background.manager

interface CartAlarmManager {
    fun schedule(productId: Int)
    fun cancel(productId: Int)
}