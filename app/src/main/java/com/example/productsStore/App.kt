package com.example.productsStore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productsStore.presentation.di.PresentationComponentHolder
import com.example.productsStore.presentation.ui.screen.ProductsListScreen
import com.example.productsStore.presentation.viewmodel.ProductsListViewModel
import com.example.productsstrore.R

@Composable
fun App() {
    // Пока один экран оставлю просто так
    val subcomponent = PresentationComponentHolder.component.createProductsListSubcomponent()
    val viewModel: ProductsListViewModel = viewModel(
        factory = subcomponent.createProductsListViewModelFactory()
    )

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.products_list_top_bar),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.systemBarsPadding().padding(start = 16.dp)
            )
        }
    ) { innerPadding ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)

        ProductsListScreen(
            modifier = screenModifier,
            viewModel = viewModel
        )
    }
}