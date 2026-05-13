package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.domain.usecase.GetProductDetailsUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductDetailsUiModel
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
    private val _uiState = MutableStateFlow<ProductDetailsUiModel>(ProductDetailsUiModel.Loading)
    val uiState = _uiState.asStateFlow()

    fun setProduct(id: Int) {
        viewModelScope.launch {
            val newState = fetchProductDetails(id)
            _uiState.update { newState }
        }
    }

    private suspend fun fetchProductDetails(id: Int) : ProductDetailsUiModel {
        return getProductDetailsUseCase.invoke(id).toUiModel()
    }
}