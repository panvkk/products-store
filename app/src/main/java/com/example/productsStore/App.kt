package com.example.productsStore

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.productsStore.presentation.ui.screen.ProductDetailsScreen
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.viewmodel.ProductDetailsViewModel
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R
import kotlinx.serialization.Serializable

@Serializable
object ProductsList
@Serializable
data class ProductDetails(val productId: Int)

@Composable
fun App(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            when {
                currentDestination?.hasRoute<ProductsList>() ?: false -> {
                    ProductsStoreTopBar(
                        stringResource(R.string.products_list_top_bar),
                        false
                    )
                }
                currentDestination?.hasRoute<ProductDetails>() ?: false -> {
                    ProductsStoreTopBar(
                        stringResource(R.string.product_details_top_bar),
                        true,
                        { navController.popBackStack() }
                    )
                }
                else -> {  }
            }
        }
    ) { innerPadding ->
        NavHost(
            startDestination = ProductsList,
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            composable<ProductsList>(
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(200)) +
                            fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(200)) +
                            fadeOut(animationSpec = tween(200) )
                }
            ) { backStackEntry ->
                val owner = remember(backStackEntry) {
                    navController.getBackStackEntry<ProductsList>()
                }
                val productsListVM = hiltViewModel<ProductsListViewModel>(
                    viewModelStoreOwner = owner
                )
                ProductsListScreen(
                    viewModel = productsListVM,
                    onClickProduct = { id: Int -> navController.navigate(ProductDetails(id)) }
                )
            }
            composable<ProductDetails>(
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(200)) +
                            fadeIn(animationSpec = tween(200))
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(200)) +
                            fadeOut(animationSpec = tween(200) )
                }
                ) { backStackEntry ->
                val args = backStackEntry.toRoute<ProductDetails>()
                val owner = remember(backStackEntry) {
                    navController.getBackStackEntry<ProductDetails>()
                }
                val productDetailsVM = hiltViewModel<ProductDetailsViewModel>(
                    viewModelStoreOwner = owner
                )
                productDetailsVM.setProduct(args.productId)

                ProductDetailsScreen(viewModel = productDetailsVM)
            }
        }
    }
}