package com.example.productsStore.presentation.background.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.GetCartedProductUseCase
import com.example.productsStore.presentation.background.notification.CartNotifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartNotificationsReceiver : BroadcastReceiver() {
    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope
    @Inject
    lateinit var getCartedProductUseCase: GetCartedProductUseCase
    @Inject
    lateinit var notifier: CartNotifier

    override fun onReceive(context: Context, intent: Intent) {
        val productId = intent.getIntExtra(EXTRAS_PRODUCT_ID, -1)
        if(productId == -1) return

        val pendingResult = goAsync()

        scope.launch {
            try {
                val cartedProduct = getCartedProductUseCase.invoke(productId)

                if(cartedProduct is Resource.Success && cartedProduct.data.isNotificationsOn) {
                    notifier.notifyCartedProduct(
                        cartedProduct.data.id,
                        cartedProduct.data.title,
                        cartedProduct.data.quantity
                    )
                }

            } finally {
                pendingResult.finish()
            }
        }

    }

    companion object {
        const val EXTRAS_PRODUCT_ID = "extras_product_id"

        fun getPendingIntent(appContext: Context, productId: Int) : PendingIntent {
            val intent = Intent(appContext, CartNotificationsReceiver::class.java).apply {
                putExtra(EXTRAS_PRODUCT_ID, productId)
            }

            return PendingIntent.getBroadcast(
                appContext,
                productId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

}