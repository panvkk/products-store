package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
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
    onAddToCart: () -> Unit
) {
    Card(
        modifier = modifier.height(dimensionResource(R.dimen.product_card_height)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(10f)
                    .fillMaxHeight()
                    .padding(start = dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    text = product.productTitle,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.brand ?: stringResource(R.string.no_brand),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(4f)
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    text = "$${product.priceInUSD}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                IconButton(
                    onClick = { onAddToCart() },
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.default_icon_container_size))
                        .clip(MaterialTheme.shapes.small)
                        .border(dimensionResource(R.dimen.default_border_width), Color.Gray, MaterialTheme.shapes.small)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_shopping_cart),
                        contentDescription = stringResource(R.string.add_to_shopping_cart_content_decription),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(dimensionResource(R.dimen.default_icon_size))
                    )
                }
            }
        }
    }
}