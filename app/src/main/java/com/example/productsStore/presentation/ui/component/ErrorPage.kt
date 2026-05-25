package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.productsstrore.R

@Composable
fun ErrorPage(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
    ) {
        Text(stringResource(R.string.error_title) + "\n" + errorMessage)
        Button(
            onClick = { onRetry() },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .width(dimensionResource(R.dimen.retry_button_width))
                .height(dimensionResource(R.dimen.retry_button_height))
        ) {
            Text(stringResource(R.string.retry_loading))
        }
    }
}