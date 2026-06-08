    package com.example.productsStore.presentation.background.manager

    import android.app.AlarmManager
    import android.content.Context
    import android.os.SystemClock
    import com.example.productsStore.presentation.background.receiver.CartNotificationsReceiver
    import dagger.hilt.android.qualifiers.ApplicationContext
    import javax.inject.Inject

    class CartAlarmManagerImpl @Inject constructor(
        @ApplicationContext private val appContext: Context
    ) : CartAlarmManager {

        private val alarmManager by lazy { appContext.getSystemService(AlarmManager::class.java) }

        override fun schedule(productId: Int) {
            val pendingIntent = CartNotificationsReceiver.getPendingIntent(appContext, productId)
            val triggerTime = SystemClock.elapsedRealtime() + CART_NOTIFICATIONS_INTERVAL
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                CART_NOTIFICATIONS_INTERVAL,
                pendingIntent
            )
        }

        override fun cancel(productId: Int) {
            val pendingIntent = CartNotificationsReceiver.getPendingIntent(appContext, productId)

            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }

        companion object {
            const val CART_NOTIFICATIONS_INTERVAL = 60 * 60 * 1000L
        }
    }