package com.example.productsStore.presentation.background.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.cart.GetCartUseCase
import com.example.productsStore.presentation.background.manager.CartAlarmManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RestoreAlarmReceiver : BroadcastReceiver() {

    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var cartAlarmManager: CartAlarmManager

    @Inject
    lateinit var getCartUseCase: GetCartUseCase

    override fun onReceive(context: Context, intent: Intent?) {
        if(intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        scope.launch {
            try {
                val cartItems = getCartUseCase.invoke().first()
                val itemsWithNotifications = cartItems.filter { it.isNotificationsOn }
                itemsWithNotifications.forEach { item ->
                    cartAlarmManager.schedule(item.product.id)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "Unknown error.")
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        private const val TAG = "RestoreAlarmReceiver"
    }
}