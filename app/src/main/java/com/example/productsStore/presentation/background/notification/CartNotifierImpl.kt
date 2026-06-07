package com.example.productsStore.presentation.background.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.productsStore.MainActivity
import com.example.productsStore.core.PRODUCT_DETAILS_DEEP_LINK
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

        val deepLinkUri = "$PRODUCT_DETAILS_DEEP_LINK/$id".toUri()

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri, appContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            appContext, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = createNotificationBuilder(
            title = productTitle,
            text = appContext.getString(R.string.cart_notification_text, quantity)
        )
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.outline_book_24,
                appContext.getString(R.string.go_to_details),
                pendingIntent
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