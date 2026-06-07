package com.example.productsStore.domain.usecase

import com.example.productsStore.domain.repository.CartRepository
import javax.inject.Inject

class UpdateNotificationStateUseCase @Inject constructor(
    private val repository: CartRepository,
    private val updateAlarmManagerUseCase: UpdateAlarmManagerUseCase
) {
    suspend operator fun invoke(productId: Int, isNotificationsOn: Boolean) {
        repository.updateIsNotificationsOn(productId, isNotificationsOn)
        updateAlarmManagerUseCase(productId, isNotificationsOn)
    }
}