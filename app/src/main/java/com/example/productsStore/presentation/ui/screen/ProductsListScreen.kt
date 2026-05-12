package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.productsStore.presentation.model.ProductsListUiState
import com.example.productsStore.presentation.ui.component.ProductCard
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel

@Composable
fun ProductsListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsListViewModel
) {
    val state = viewModel.uiState.collectAsState().value

    Column(modifier = modifier) {
        when(state) {
            is ProductsListUiState.Loading -> {
                Text("Загрузка...")
            }
            is ProductsListUiState.Content -> {
                val products = state.products
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(products, { it.id }) { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            else -> {  }
        }
    }
}