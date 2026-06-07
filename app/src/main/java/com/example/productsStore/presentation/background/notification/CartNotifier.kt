package com.example.productsStore.presentation.background.notification

interface CartNotifier {
    fun createNotificationChannel()
    fun notifyCartedProduct(id: Int, productTitle: String, quantity: Int)
}