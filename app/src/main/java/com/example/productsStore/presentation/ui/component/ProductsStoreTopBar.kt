package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.productsStore.presentation.ui.screen.destination.CartDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductDetailsDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductsListDestination
import com.example.productsstrore.R

@Composable
fun ProductsStoreTopBar(
    navController: NavHostController,
    showCartHint: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigateToCart = {
        navController.navigate(CartDestination) {
            popUpTo(ProductsListDestination) { inclusive = false }
            launchSingleTop = true
        }
    }

    when {
        currentDestination?.hasRoute<ProductsListDestination>() ?: false -> {
            ApplicationTopBar(
                title = stringResource(R.string.products_list_top_bar),
                canNavigateUp = false,
                topBarAction = TopBarAction.GoCart,
                onClickAction = navigateToCart
            )
        }
        currentDestination?.hasRoute<ProductDetailsDestination>() ?: false -> {
            ApplicationTopBar(
                title = stringResource(R.string.product_details_top_bar),
                canNavigateUp = true,
                onNavigateUp = { navController.popBackStack() },
                onClickAction = navigateToCart,
                topBarAction = TopBarAction.GoCart
            )
        }
        currentDestination?.hasRoute<CartDestination>() ?: false -> {
            ApplicationTopBar(
                title = stringResource(R.string.cart_top_bar),
                canNavigateUp = true,
                topBarAction = TopBarAction.Info,
                onClickAction = showCartHint,
                onNavigateUp = { navController.popBackStack() }
            )
        }
        else -> {
            ApplicationTopBar(
                title = stringResource(R.string.default_top_bar),
                canNavigateUp = false,
                topBarAction = TopBarAction.GoCart,
                onClickAction = navigateToCart
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    title: String,
    canNavigateUp: Boolean,
    onNavigateUp: () -> Unit = {  },
    onClickAction: () -> Unit = {  },
    topBarAction: TopBarAction
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            if(canNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back_content_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clickable { onNavigateUp() }
                        .padding(end = dimensionResource(R.dimen.small_padding))
                        .size(dimensionResource(R.dimen.default_icon_size))
                )
            }
        },
        actions = {
            when(topBarAction) {
                TopBarAction.GoCart -> {
                    Icon(
                        painter = painterResource(R.drawable.shopping_cart),
                        contentDescription = stringResource(R.string.navigate_to_cart_content_description),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable { onClickAction() }
                            .padding(end = dimensionResource(R.dimen.small_padding))
                            .size(dimensionResource(R.dimen.default_icon_size))
                    )
                }
                TopBarAction.Info -> {
                    Icon(
                        painter = painterResource(R.drawable.info),
                        contentDescription = stringResource(R.string.info_icon_description),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable { onClickAction() }
                            .padding(end = dimensionResource(R.dimen.small_padding))
                            .size(dimensionResource(R.dimen.default_icon_size))
                    )
                }
                TopBarAction.NoAction -> {  }
            }
        },
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding))
    )
}

sealed interface TopBarAction {
    data object Info : TopBarAction
    data object GoCart : TopBarAction
    data object NoAction : TopBarAction
}