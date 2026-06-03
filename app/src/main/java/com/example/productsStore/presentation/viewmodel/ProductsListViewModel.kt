package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tinkoff.kotea.core.Store
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    val store: @JvmSuppressWildcards Store<ProductsListState, ProductsListEvent.Ui, ProductsListNews>
) : ViewModel() {
    init {
        store.launchIn(viewModelScope)
    }
}