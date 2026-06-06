package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.example.productsStore.presentation.model.CartItemUiModel
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags.PRODUCT_QUANTITY
import com.example.productsstrore.R

@Composable
fun CartItem(
    cartItemModel: CartItemUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.small_padding))
    ) {
        ProductCard(
            product = cartItemModel.product,
            canBeAddedToCart = false,
            modifier = Modifier
                .weight(8f)
                .clickable { onClick(cartItemModel.product.id) }
        )
        Text(
            text = "x${cartItemModel.quantity}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f).testTag(PRODUCT_QUANTITY)
        )
    }
}