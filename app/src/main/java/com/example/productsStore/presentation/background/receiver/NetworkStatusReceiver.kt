package com.example.productsStore.presentation.background.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.config.UpdateNetworkStatusUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NetworkStatusReceiver @Inject constructor() : BroadcastReceiver() {

    private var isRegistered = false
    @Inject
    lateinit var updateNetworkStatusUseCase: UpdateNetworkStatusUseCase
    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            updateConnectivityStatus(context)
        }
    }

    fun register() {
        if(!isRegistered) {
            ContextCompat.registerReceiver(
                appContext, this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),
                ContextCompat.RECEIVER_EXPORTED
            )
            isRegistered = true
            updateConnectivityStatus(appContext)
        }
    }

    fun unregister() {
        if(isRegistered) {
            appContext.unregisterReceiver(this)
            isRegistered = false
        }
    }

    private fun updateConnectivityStatus(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo?.isConnected ?: false

        scope.launch { updateNetworkStatusUseCase(isConnected) }
    }
}