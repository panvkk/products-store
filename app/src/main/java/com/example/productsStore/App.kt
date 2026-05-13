package com.example.productsStore

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.products_list_top_bar),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(start = 16.dp)
            )
        }
    ) { innerPadding ->
        NavHost(
            startDestination = ProductsList,
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            composable<ProductsList> { backStackEntry ->
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
            composable<ProductDetails> { backStackEntry ->
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