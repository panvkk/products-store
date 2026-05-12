package com.example.productsStore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel

@Composable
fun App() {
    val viewModel = ProductsListViewModel()
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