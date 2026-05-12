package com.example.productsStore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productsStore.presentation.di.PresentationComponentHolder
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel

@Composable
fun App() {
    // Пока один экран оставлю просто так
    val subcomponent = PresentationComponentHolder.component.createProductsListSubcomponent()
    val viewModel: ProductsListViewModel = viewModel(
        factory = subcomponent.createProductsListViewModelFactory()
    )

    Scaffold { innerPadding ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        ProductsListScreen(
            modifier = screenModifier,
            viewModel = viewModel
        )
    }
}