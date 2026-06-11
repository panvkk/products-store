package com.example.productsStore.presentation.ui.component.toast

import com.example.productsStore.core.di.ApplicationMainScope
import com.example.productsStore.presentation.model.SmartToastModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastManager @Inject constructor(
    @ApplicationMainScope val scope: CoroutineScope
) {
    private val _currentToast = MutableStateFlow<SmartToastModel?>(null)
    val currentToast = _currentToast.asStateFlow()

    private val _toastIsVisible = MutableStateFlow(false)
    val toastIsVisible = _toastIsVisible.asStateFlow()

    private var currentJob: Job? = null

    fun show(smartToast: SmartToastModel) {
        dismiss()

        _currentToast.update { smartToast }
        _toastIsVisible.update { true }

        currentJob = scope.launch {
            delay(smartToast.duration)
            _toastIsVisible.update { false }
        }
    }

    fun dismiss() {
        currentJob?.cancel()
        _toastIsVisible.update { false }
    }
}