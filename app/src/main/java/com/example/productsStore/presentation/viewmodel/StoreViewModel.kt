package com.example.productsStore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.contract.ProductDetailsNews
import com.example.productsStore.presentation.contract.ProductDetailsState
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tinkoff.kotea.core.Store
import javax.inject.Inject

abstract class StoreViewModel<State: Any, Event: Any, News: Any>(
    val store: Store<State, Event, News>
) : ViewModel() {
    init {
        store.launchIn(viewModelScope)
    }
}

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    store: @JvmSuppressWildcards Store<ProductsListState, ProductsListEvent.Ui, ProductsListNews>
) : StoreViewModel<ProductsListState, ProductsListEvent.Ui, ProductsListNews>(store)

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    store: @JvmSuppressWildcards Store<ProductDetailsState, ProductDetailsEvent.Ui, ProductDetailsNews>
) : StoreViewModel<ProductDetailsState, ProductDetailsEvent.Ui, ProductDetailsNews>(store)