package com.example.productsStore.domain.usecase

import com.example.productsStore.data.local.ConfigDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNetworkStatusUseCase @Inject constructor(
    private val dataStore: ConfigDataStore
) {
    operator fun invoke() : Flow<Boolean> = dataStore.isOnlineFlow
}