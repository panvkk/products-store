package com.example.productsStore.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.ui.component.ErrorPage
import com.example.productsStore.presentation.ui.component.ProductCard
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R

@Composable
fun ProductsListScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (id: Int) -> Unit,
    viewModel: ProductsListViewModel
) {
    val context = LocalContext.current
    val store = viewModel.store
    val state: ProductsListState by store.state.collectAsStateWithLifecycle()
    val itemsCountBeforeFetch = state.pageSize / 4

    LaunchedEffect(store.news) {
        store.news.collect { new ->
            when(new) {
                is ProductsListNews.NavigateToDetails -> navigateToDetails(new.productId)
                is ProductsListNews.ShowAddedToCartToast -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.product_added_to_cart_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val itemHeight = with(LocalDensity.current) {
            dimensionResource(R.dimen.product_card_height).toPx()
        }
        val maxItems = (constraints.maxHeight / itemHeight).toInt()

        LaunchedEffect(maxItems) {
            store.dispatch(ProductsListEvent.Ui.OnSetupPageSize(maxItems * 2))
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = state.products,
                key = { _, item -> item.id }
            ) { index, product ->
                ProductCard(
                    product = product,
                    onAddToCart = { store.dispatch(ProductsListEvent.Ui.OnAddToCart(product)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.small_padding))
                        .clickable {
                            store.dispatch(ProductsListEvent.Ui.OnNavigateDetails(product.id))
                        }
                )
                if(index + itemsCountBeforeFetch == state.products.size - 1
                    && state.error == null) {
                    store.dispatch(ProductsListEvent.Ui.OnLoadNextPage)
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
                    ErrorPage(errorMessage, { store.dispatch(ProductsListEvent.Ui.OnLoadNextPage) })
                }
            }
        }
    }
}