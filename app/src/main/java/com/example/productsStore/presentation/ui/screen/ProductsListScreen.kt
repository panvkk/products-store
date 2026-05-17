package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.ProductsListUiState
import com.example.productsStore.presentation.ui.component.ProductCard
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R

@Composable
fun ProductsListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsListViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(modifier = modifier) {
        when(state) {
            is ProductsListUiState.Loading -> {
                Text(stringResource(R.string.loading_title))
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
            is ProductsListUiState.Error -> {
                val errorMessage = when(state.error) {
                    is DomainError.NetworkIssue -> stringResource(R.string.check_your_internet_connection)
                    is DomainError.ServerIssue -> stringResource(R.string.please_try_again_later)
                    else -> stringResource(R.string.unknown_error)
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.error_title) + "\n" + errorMessage)
                    Button(onClick = { viewModel.fetchProducts() }) {
                        Text(stringResource(R.string.retry_loading))
                    }
                }
            }
        }
    }
}