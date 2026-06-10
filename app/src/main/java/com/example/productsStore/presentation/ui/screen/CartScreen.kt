package com.example.productsStore.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartNews
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.ui.component.CartListItem
import com.example.productsStore.presentation.ui.screen.testing.lazyListItemPosition
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.CART_ITEM
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.CART_ITEMS_LAZY_COLUMN
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.CART_SIZE_TITLE
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.CLEAR_CART_BUTTON
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.ROOT_TAG
import com.example.productsStore.presentation.viewmodel.CartViewModel
import com.example.productsstrore.R

@Composable
internal fun CartScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    viewModel: CartViewModel,
    showCartHint: () -> Unit
) {
    val context = LocalContext.current
    val store = viewModel.store
    val state by store.state.collectAsStateWithLifecycle()

    LaunchedEffect(store.news) {
        store.news.collect { new ->
            when(new) {
                is CartNews.NavigateToDetails -> navigateToDetails(new.productId)
                is CartNews.ShowCartClearedToast -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.cart_cleared_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is CartNews.ShowCartHintDialog -> { showCartHint() }
            }
        }
    }
    CartScreenContent(
        modifier = modifier,
        state = state,
        onClickItem = { productId ->
            store.dispatch(
                CartEvent.Ui.OnNavigateToDetails(productId)
            )
        },
        onClearCart = { store.dispatch(CartEvent.Ui.OnClearCart) },
        onLongClickItem = { id, newIsNotificationsOn ->
            store.dispatch(
                CartEvent.Ui.OnUpdateNotifications(
                    id,
                    newIsNotificationsOn
                )
            )
        }
    )
}

@Composable
internal fun CartScreenContent(
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit,
    onLongClickItem: (Int, Boolean) -> Unit,
    onClearCart: () -> Unit,
    state: CartState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(ROOT_TAG)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CART_ITEMS_LAZY_COLUMN),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is CartState.Content -> {
                    item {
                        Text(
                            text = if (state.cartItems.isEmpty())
                                stringResource(R.string.empty_cart)
                            else
                                stringResource(R.string.cart_size_title, state.cartSize),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .padding(vertical = dimensionResource(R.dimen.small_padding))
                                .testTag(CART_SIZE_TITLE)
                        )
                    }
                    itemsIndexed(
                        items = state.cartItems,
                        key = { _, item -> item.product.id },
                    ) { index, cartItem ->
                        CartListItem(
                            cartItem,
                            modifier = Modifier
                                .lazyListItemPosition(index)
                                .testTag(CART_ITEM),
                            onClick = { onClickItem(cartItem.product.id) },
                            onLongClick = { onLongClickItem(cartItem.product.id, !cartItem.isNotificationsOn) }
                        )
                    }
                }

                is CartState.Error -> {
                    item {
                        val errorMessage = when (state.error) {
                            is DomainError.NetworkIssue -> stringResource(R.string.check_your_internet_connection)
                            is DomainError.ServerIssue -> stringResource(R.string.please_try_again_later)
                            else -> stringResource(R.string.unknown_error)
                        }
//                      ErrorPage(errorMessage, {  })
                    }
                }

                is CartState.Loading -> {
                    item { Text(stringResource(R.string.loading_title)) }
                }
            }
        }
        if(state is CartState.Content && state.cartItems.isNotEmpty()) {
            FloatingActionButton(
                onClick = { onClearCart() },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.large_padding))
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.clear_cart_button_height))
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.BottomCenter)
                    .testTag(CLEAR_CART_BUTTON)
            ) {
                Text(
                    text = stringResource(R.string.clear_cart_button),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}