package com.example.productsStore.domain.usecase

import com.example.productsStore.presentation.background.manager.CartAlarmManager
import javax.inject.Inject

class UpdateAlarmManagerUseCase @Inject constructor(
    private val cartAlarmManager: CartAlarmManager
) {
    operator fun invoke(productId: Int, isNotificationsOn: Boolean) {
        if(isNotificationsOn) {
            cartAlarmManager.schedule(productId)
        } else {
            cartAlarmManager.cancel(productId)
        }
    }
}