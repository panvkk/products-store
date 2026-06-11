package com.example.productsStore

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.productsStore.core.PRODUCT_DETAILS_DEEP_LINK
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.model.SmartToastModel
import com.example.productsStore.presentation.ui.component.CartHintDialog
import com.example.productsStore.presentation.ui.component.NoInternetIndicator
import com.example.productsStore.presentation.ui.component.ProductsStoreTopBar
import com.example.productsStore.presentation.ui.component.toast.SmartToastHost
import com.example.productsStore.presentation.ui.component.toast.ToastManager
import com.example.productsStore.presentation.ui.screen.CartScreen
import com.example.productsStore.presentation.ui.screen.ProductDetailsScreen
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.ui.screen.destination.CartDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductDetailsDestination
import com.example.productsStore.presentation.ui.screen.destination.ProductsListDestination
import com.example.productsStore.presentation.viewmodel.CartViewModel
import com.example.productsStore.presentation.viewmodel.GlobalViewModel
import com.example.productsStore.presentation.viewmodel.ProductDetailsViewModel
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R


@Composable
fun App(
    navController: NavHostController,
    toastManager: ToastManager
) {
    val globalViewModel = hiltViewModel<GlobalViewModel>()
    val isOnline by globalViewModel.isOnline.collectAsStateWithLifecycle()
    var shouldDisplayCartHint by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ProductsStoreTopBar(navController, showCartHint = { shouldDisplayCartHint = true })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
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
                        navigateToDetails = { id: Int -> navController.navigate(ProductDetailsDestination(id)) },
                        showToast = { toast: SmartToastModel -> toastManager.show(toast) }
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
                    },
                    deepLinks = listOf(
                        navDeepLink<ProductDetailsDestination>(
                            basePath = PRODUCT_DETAILS_DEEP_LINK
                        )
                    )
                ) { backStackEntry ->
                    val args = backStackEntry.toRoute<ProductDetailsDestination>()
                    val productDetailsVM = hiltViewModel<ProductDetailsViewModel>()

                    LaunchedEffect(args) {
                        productDetailsVM.store.dispatch(
                            ProductDetailsEvent.Ui.SetProductId(args.productId)
                        )
                    }

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
                        navigateToDetails = { id: Int ->
                            navController.navigate(ProductDetailsDestination(id))
                        },
                        viewModel = cartViewModel,
                        showToast = { toast: SmartToastModel -> toastManager.show(toast) },
                        showCartHint = { shouldDisplayCartHint = true }
                    )
                }
            }
            if(!isOnline) {
                NoInternetIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
            SmartToastHost(
                toastManager,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .padding(dimensionResource(R.dimen.large_padding))
            )
        }
        if(shouldDisplayCartHint) {
            CartHintDialog(onDismiss = { shouldDisplayCartHint = false })
        }
    }
}