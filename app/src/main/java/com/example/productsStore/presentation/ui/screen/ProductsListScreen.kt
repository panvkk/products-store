package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val indexWhenFetchNextPage = viewModel.indexWhenFetchNextPage.collectAsState().value

    BoxWithConstraints(modifier = modifier) {
        val itemHeight = with(LocalDensity.current) {
            dimensionResource(R.dimen.product_card_height).toPx()
        }
        val maxItems = (constraints.maxHeight / itemHeight).toInt()

        LaunchedEffect(maxItems) {
            viewModel.setupPageSize(maxItems * 2)
        }
        Column {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(state.products) { index, product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    if(index == indexWhenFetchNextPage) {
                        viewModel.fetchNextPage()
                    }
                }
                item {
                    if(state.isLoadingGoing) {
                        Text("Загрузка...")
                    }
                }
            }
        }
    }
}