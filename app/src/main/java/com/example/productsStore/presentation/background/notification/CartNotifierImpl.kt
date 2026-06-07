package com.example.productsStore.presentation.background.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.productsStore.core.presentation.isNotificationPermissionGranted
import com.example.productsstrore.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CartNotifierImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : CartNotifier {

    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(appContext)
    }

    override fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            appContext.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun notifyCartedProduct(id: Int, productTitle: String, quantity: Int) {
        createNotificationChannel()

        val notification = createNotificationBuilder(
            title = productTitle,
            text = appContext.getString(R.string.cart_notification_text, quantity)
        )
            .setAutoCancel(true)
            .build()

        safeNotify(id, notification)
    }


    private fun createNotificationBuilder(
        title: String,
        text: String,
    ) : NotificationCompat.Builder {
        return NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_book_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(title)
            .setContentText(text)

    }

    @SuppressLint("MissingPermission")
    private fun safeNotify(id: Int, notification: Notification) {
        if(appContext.isNotificationPermissionGranted()) {
            notificationManager.notify(id, notification)
        }
    }

    private companion object {
        const val CHANNEL_ID = "cart_channel"
    }
}