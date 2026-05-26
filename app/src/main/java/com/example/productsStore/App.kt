package com.example.productsStore

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.productsStore.presentation.ui.component.ProductsStoreTopBar
import com.example.productsStore.presentation.ui.screen.CartScreen
import com.example.productsStore.presentation.ui.screen.ProductDetailsScreen
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.ui.screen.destination.CartDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductDetailsDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductsListDestination
import com.example.productsStore.presentation.viewmodel.CartViewModel
import com.example.productsStore.presentation.viewmodel.ProductDetailsViewModel
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R


@Composable
fun App(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigateToCart = {
        navController.navigate(CartDestination) {
            popUpTo(CartDestination) { inclusive = false }
            launchSingleTop = true
        }
    }
    Scaffold(
        topBar = {
            when {
                currentDestination?.hasRoute<ProductsListDestination>() ?: false -> {
                    ProductsStoreTopBar(
                        title = stringResource(R.string.products_list_top_bar),
                        canNavigateUp = false,
                        canNavigateToCart = true,
                        navigateToCart = navigateToCart
                    )
                }
                currentDestination?.hasRoute<ProductDetailsDestination>() ?: false -> {
                    ProductsStoreTopBar(
                        title = stringResource(R.string.product_details_top_bar),
                        canNavigateUp = true,
                        canNavigateToCart = true,
                        navigateUp = { navController.popBackStack() },
                        navigateToCart = navigateToCart
                    )
                }
                currentDestination?.hasRoute<CartDestination>() ?: false -> {
                    ProductsStoreTopBar(
                        title = stringResource(R.string.cart_top_bar),
                        canNavigateUp = true,
                        canNavigateToCart = false,
                        navigateUp = { navController.navigate(ProductsListDestination) }
                    )
                }
                else -> {
                    ProductsStoreTopBar(
                        title = stringResource(R.string.default_top_bar),
                        canNavigateUp = false,
                        canNavigateToCart = true,
                        navigateToCart = navigateToCart
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            startDestination = ProductsListDestination,
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            composable<ProductsListDestination>(
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(200)) +
                            fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(200)) +
                            fadeOut(animationSpec = tween(200) )
                }
            ) {
                val productsListVM = hiltViewModel<ProductsListViewModel>()

                ProductsListScreen(
                    viewModel = productsListVM,
                    onClickProduct = { id: Int -> navController.navigate(ProductDetailsDestination(id)) }
                )
            }
            composable<ProductDetailsDestination>(
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(200)) +
                            fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(200)) +
                            fadeOut(animationSpec = tween(200) )
                }
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<ProductDetailsDestination>()
                val productDetailsVM = hiltViewModel<ProductDetailsViewModel>()

                LaunchedEffect(args) { productDetailsVM.setProductId(args.productId) }

                ProductDetailsScreen(viewModel = productDetailsVM)
            }
            composable<CartDestination>(
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(200)) +
                            fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(200)) +
                            fadeOut(animationSpec = tween(200) )
                }
            ) {
                val cartViewModel = hiltViewModel<CartViewModel>()

                CartScreen(
                    onClickProduct = { id: Int ->
                        navController.navigate(ProductDetailsDestination(id))
                    },
                    viewModel = cartViewModel
                )
            }
        }
    }
}