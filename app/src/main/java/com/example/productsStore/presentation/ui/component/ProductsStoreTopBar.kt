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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.productsstrore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsStoreTopBar(
    title: String,
    canNavigateUp: Boolean,
    canNavigateToCart: Boolean,
    navigateUp: () -> Unit = {  },
    navigateToCart: () -> Unit = {  }
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
                        .clickable { navigateUp() }
                        .padding(end = dimensionResource(R.dimen.small_padding))
                        .size(dimensionResource(R.dimen.default_icon_size))
                )
            }
        },
        actions = {
            if(canNavigateToCart) {
                Icon(
                    painter = painterResource(R.drawable.shopping_cart),
                    contentDescription = stringResource(R.string.navigate_to_cart_content_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clickable { navigateToCart() }
                        .padding(end = dimensionResource(R.dimen.small_padding))
                        .size(dimensionResource(R.dimen.default_icon_size))
                )
            }
        },
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding))
    )
}