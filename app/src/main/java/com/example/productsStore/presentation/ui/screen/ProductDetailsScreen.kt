package com.example.productsStore.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.model.ProductDetailsUiState
import com.example.productsStore.presentation.ui.component.ErrorPage
import com.example.productsStore.presentation.ui.component.ProductInfoRow
import com.example.productsStore.presentation.ui.theme.InStockColor
import com.example.productsStore.presentation.ui.theme.RatingStarColor
import com.example.productsStore.presentation.viewmodel.ProductDetailsViewModel
import com.example.productsstrore.R

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when(state) {
            is ProductDetailsUiState.Content -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = state.productDetails.productTitle,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$${state.productDetails.priceInUSD}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = RatingStarColor
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = state.productDetails.rating.toString(),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    Text(
                        text = state.productDetails.availabilityStatus,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (state.productDetails.availabilityStatus.contains("In Stock", ignoreCase = true))
                            InStockColor else MaterialTheme.colorScheme.error
                    )
                }

                HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.description_title),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProductInfoRow(label = stringResource(R.string.weight_title), value = "${state.productDetails.weight}" + stringResource(R.string.gramm))
                        ProductInfoRow(label = stringResource(R.string.warranty_title), value = state.productDetails.warrantyInformation)
                    }
                }
            }
            is ProductDetailsUiState.Error -> {
                val errorMessage = when(state.error) {
                    is DomainError.NetworkIssue -> stringResource(R.string.check_your_internet_connection)
                    is DomainError.ServerIssue -> stringResource(R.string.please_try_again_later)
                    else -> stringResource(R.string.unknown_error)
                }
                ErrorPage(errorMessage, { viewModel.fetchProductDetails() })
            }
            is ProductDetailsUiState.Loading -> {
                Text(stringResource(R.string.loading_title))
            }
        }
    }
}
