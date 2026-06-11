package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.productsStore.presentation.model.ProductUiModel
import com.example.productsstrore.R

@Composable
fun ProductCard(
    product: ProductUiModel,
    modifier: Modifier = Modifier,
    canBeAddedToCart: Boolean = true,
    onAddToCart: () -> Unit = {  }
) {
    Card(
        modifier = modifier.height(dimensionResource(R.dimen.product_card_height)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.large_padding))
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.productTitle,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$${product.priceInUSD}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.brand ?: stringResource(R.string.no_brand),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                if(canBeAddedToCart) {
                    IconButton(
                        onClick = { onAddToCart() },
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.default_icon_container_size))
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_shopping_cart),
                            contentDescription = stringResource(R.string.add_to_shopping_cart_content_decription),
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(dimensionResource(R.dimen.default_icon_size))
                        )
                    }
                }
            }
        }
    }
}