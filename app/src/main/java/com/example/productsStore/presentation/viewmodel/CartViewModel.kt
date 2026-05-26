package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.ClearCartUseCase
import com.example.productsStore.domain.usecase.GetCartUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    val uiState: StateFlow<CartUiState> = getCartUseCase.invoke().map { resource ->
        when(resource) {
            is Resource.Success -> {
                CartUiState.Content(resource.data.map { it.toUiModel() })
            }
            is Resource.Error -> {
                CartUiState.Error(resource.error)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CartUiState.Loading)

    fun clearCart() {
        applicationScope.launch {
            clearCartUseCase()
        }
    }
}