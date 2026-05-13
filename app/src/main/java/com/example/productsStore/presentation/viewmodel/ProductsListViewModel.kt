package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.core.Resource
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsListUiState>(ProductsListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init { fetchProducts() }

    fun fetchProducts() {
        _uiState.update { ProductsListUiState.Loading }
        viewModelScope.launch {
            val result = getProductsUseCase.invoke()
            _uiState.value =
                when(result) {
                    is Resource.Success -> ProductsListUiState.Content(result.data.map { it.toUiModel() })
                    is Resource.Error -> ProductsListUiState.Error(result.error)
                }
        }
    }
}