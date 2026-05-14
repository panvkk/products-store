package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.core.Resource
import com.example.productsStore.domain.usecase.GetProductDetailsUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _productId = MutableStateFlow<Int?>(null)

    init {
        viewModelScope.launch {
            _productId.collect { id -> id?.let { fetchProductDetails(); return@collect } }
        }
    }

    fun setProductId(id: Int) { _productId.update { id } }

    fun fetchProductDetails() {
        val productId: Int = _productId.value ?: return

        viewModelScope.launch {
            val result = getProductDetailsUseCase.invoke(productId)
            val newState = when (result) {
                is Resource.Success -> {
                    ProductDetailsUiState.Content(result.data.toUiModel())
                }

                is Resource.Error -> {
                    ProductDetailsUiState.Error(result.error)
                }
            }
            _uiState.update { newState }
        }
    }
}