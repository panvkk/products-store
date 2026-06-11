package com.example.productsStore

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.productsStore.presentation.background.receiver.NetworkStatusReceiver
import com.example.productsStore.presentation.ui.component.toast.ToastManager
import com.example.productsStore.presentation.ui.theme.ProductsStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkReceiver: NetworkStatusReceiver
    @Inject
    lateinit var toastManager: ToastManager
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val rememberNavController = rememberNavController()
            LaunchedEffect(rememberNavController) {
                navController = rememberNavController

                intent?.let { rememberNavController.handleDeepLink(it) }
            }
            ProductsStoreTheme {
                App(rememberNavController, toastManager)
            }
        }
        requestPostNotificationPermission(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navController?.handleDeepLink(intent)
    }

    override fun onStart() {
        super.onStart()
        networkReceiver.register()
    }

    override fun onStop() {
        super.onStop()
        networkReceiver.unregister()
    }

    private fun requestPostNotificationPermission(context: Context) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        if (ContextCompat.checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPostNotificationPermissionLauncher.launch(permission)
        }
    }

    private val requestPostNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {  }
}