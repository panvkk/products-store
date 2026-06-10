package com.example.productsStore.domain.usecase.config

import com.example.productsStore.data.local.ConfigDataStore
import javax.inject.Inject

class UpdateNetworkStatusUseCase @Inject constructor(
    private val dataStore: ConfigDataStore
) {
    suspend operator fun invoke(isOnline: Boolean) {
        dataStore.updateOnlineStatus(isOnline)
    }
}