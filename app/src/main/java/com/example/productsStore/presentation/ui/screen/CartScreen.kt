package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.CartUiState
import com.example.productsStore.presentation.ui.component.ProductCard
import com.example.productsStore.presentation.viewmodel.CartViewModel
import com.example.productsstrore.R

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onClickProduct: (Int) -> Unit,
    viewModel: CartViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(state) {
                is CartUiState.Content -> {
                    if(state.cartItems.isEmpty()) {
                        item { Text(stringResource(R.string.empty_cart)) }
                    } else {
                        items(state.cartItems) { cartItem ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimensionResource(R.dimen.small_padding))

                            ) {
                                ProductCard(
                                    product = cartItem.product,
                                    canBeAddedToCart = false,
                                    modifier = Modifier
                                        .weight(8f)
                                        .clickable { onClickProduct(cartItem.product.id) }
                                )
                                Text(
                                    text = "x${cartItem.quantity}",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                is CartUiState.Error -> {
                    item {
                        val errorMessage = when(state.error) {
                            is DomainError.NetworkIssue -> stringResource(R.string.check_your_internet_connection)
                            is DomainError.ServerIssue -> stringResource(R.string.please_try_again_later)
                            else -> stringResource(R.string.unknown_error)
                        }
//                    ErrorPage(errorMessage, { viewModel.fetchProductDetails() })
                    }
                }
                is CartUiState.Loading -> {
                    item { Text(stringResource(R.string.loading_title)) }
                }
            }
        }
        FloatingActionButton(
            onClick = { viewModel.clearCart() },
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.large_padding))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.clear_cart_button_height))
                .clip(MaterialTheme.shapes.medium)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.clear_cart_button),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}
