package com.example.productsStore.domain.usecase.config

import com.example.productsStore.data.local.ConfigDataStore
import javax.inject.Inject

class UpdateShouldDisplayCartHintUseCase @Inject constructor(
    private val dataStore: ConfigDataStore
) {
    suspend operator fun invoke() { dataStore.updateShouldDisplayCartHint() }
}