package com.example.productsStore.domain.usecase

import com.example.productsStore.data.local.ConfigDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNetworkStatusUseCase @Inject constructor(
    private val dataStore: ConfigDataStore
) {
    suspend operator fun invoke(isOnline: Boolean) {
        dataStore.updateOnlineStatus(isOnline)
    }
}