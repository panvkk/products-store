package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.ui.component.ProductCard
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R

@Composable
fun ProductsListScreen(
    modifier: Modifier = Modifier,
    onClickProduct: (id: Int) -> Unit,
    viewModel: ProductsListViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val itemsCountBeforeFetch = viewModel.itemsCountBeforeFetch.collectAsStateWithLifecycle().value

    BoxWithConstraints(modifier = modifier) {
        val itemHeight = with(LocalDensity.current) {
            dimensionResource(R.dimen.product_card_height).toPx()
        }
        val maxItems = (constraints.maxHeight / itemHeight).toInt()

        LaunchedEffect(maxItems) {
            viewModel.setupPageSize(maxItems * 2)
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(state.products) { index, product ->
                ProductCard(
                    product = product,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onClickProduct(product.id) }
                )
                if(index + itemsCountBeforeFetch == state.products.size - 1
                    && state.error == null) {
                    viewModel.fetchNextPage()
                }
            }
            item {
                if(state.isLoadingGoing) {
                    Text(stringResource(R.string.loading_title))
                } else if(state.error != null) {
                    val errorMessage = when(state.error) {
                        is DomainError.NetworkIssue -> stringResource(R.string.check_your_internet_connection)
                        is DomainError.ServerIssue -> stringResource(R.string.please_try_again_later)
                        else -> stringResource(R.string.unknown_error)
                    }
                    Text(stringResource(R.string.error_title) + "\n" + errorMessage)
                    Button(onClick = { viewModel.fetchNextPage() }) {
                        Text(stringResource(R.string.retry_loading))
                    }
                }
            }
        }
    }
}