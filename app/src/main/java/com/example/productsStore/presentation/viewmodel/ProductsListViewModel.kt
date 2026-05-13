package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.core.Resource
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(generateInitialState())
    val uiState = _uiState.asStateFlow()

    private val _pageSize = MutableStateFlow<Int?>(null)
    val indexWhenFetchNextPage =
        combine(
            _pageSize,
            _uiState.map { it.products }.distinctUntilChanged()
        ) { pageSize, products ->
            val productsCount = products.size
            productsCount - (pageSize?.div(4) ?: 0) - 1
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), 0)


    init { fetchProducts() }

    fun fetchProducts() {
        _uiState.update { ProductsListUiState.Loading }
        viewModelScope.launch {
            _pageSize.collect { pageSize ->
                pageSize?.let {
                    fetchNextPage()
                    return@collect
                }
            }
        }
    }

    fun fetchNextPage() {
        val currentState = _uiState.value
        if(currentState.isLoadingGoing || currentState.isLastPageReached) return

        _uiState.update { it.copy(isLoadingGoing = true) }
        viewModelScope.launch {
            val skip = currentState.products.size
            val limit = _pageSize.value!!
            val newProducts = getProductsUseCase.invoke(skip = skip, limit = limit)
                .map { it.toUiModel() }

            _uiState.update { state ->
                state.copy(
                    products = state.products + newProducts,
                    isLoadingGoing = false,
                    isLastPageReached = newProducts.size < limit
                )
            }
        }
    }
    fun setupPageSize(pageSize: Int) { _pageSize.update { pageSize } }

    private fun generateInitialState() : ProductsListUiState {
        return ProductsListUiState(
            isLoadingGoing = false,
            isLastPageReached = false,
            products = emptyList()
        )
    }
}