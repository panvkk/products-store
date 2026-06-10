package com.example.productsStore.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.productsStore.core.PRODUCT_AVAILABILITY_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_BRAND_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_PRICE_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_RATING_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_TITLE_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_WARRANTY_PLACEHOLDER
import com.example.productsStore.core.PRODUCT_WEIGHT_PLACEHOLDER
import com.example.productsStore.presentation.model.ProductUiModel
import com.example.productsStore.presentation.ui.screen.testing.testtags.ProductDetailsTestTags.PRODUCT_IMAGE
import com.example.productsStore.presentation.ui.theme.InStockColor
import com.example.productsStore.presentation.ui.theme.RatingStarColor
import com.example.productsstrore.R

@Composable
fun TextPlaceHolder(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text), style = style)

    val placeholderWidth = with(density) { textLayoutResult.size.width.toDp() }
    val placeholderHeight = with(density) { textLayoutResult.size.height.toDp() }

    Box(
        modifier = modifier.width(placeholderWidth)
            .height(placeholderHeight)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    )
}

@Composable
fun ProductCardPlaceholder(modifier: Modifier = Modifier) {
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
                TextPlaceHolder(
                    text = PRODUCT_TITLE_PLACEHOLDER,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
                )
                TextPlaceHolder(
                    text = PRODUCT_BRAND_PLACEHOLDER,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
                modifier = Modifier
                    .weight(4f)
                    .padding(dimensionResource(R.dimen.small_padding))
            ) {
                TextPlaceHolder(
                    text = PRODUCT_PRICE_PLACEHOLDER,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
                )
            }
        }
    }
}

@Composable
fun ProductDetailsScreenPlaceholder() {
    Image(
        painter = painterResource(R.drawable.loading_image_2),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.product_image_height))
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        TextPlaceHolder(
            text = PRODUCT_TITLE_PLACEHOLDER,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(2f)
                .shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
        )
        TextPlaceHolder(
            text = PRODUCT_PRICE_PLACEHOLDER,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(R.dimen.small_padding))
                .shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding))
    ) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Row(
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding), vertical = dimensionResource(R.dimen.extra_small_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.large_padding)),
                    tint = RatingStarColor
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.extra_small_padding)))
                TextPlaceHolder(
                    text = PRODUCT_RATING_PLACEHOLDER,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
                )
            }
        }
        TextPlaceHolder(
            text = PRODUCT_AVAILABILITY_PLACEHOLDER,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
        )
    }

    HorizontalDivider(
        thickness = dimensionResource(R.dimen.divider_thickness),
        color = MaterialTheme.colorScheme.outlineVariant
    )

    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))) {
        Text(
            text = stringResource(R.string.description_title),
            style = MaterialTheme.typography.labelMedium,
        )
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
        ) {
            ProductInfoPlaceholder(
                label = stringResource(R.string.weight_title),
                value = PRODUCT_WEIGHT_PLACEHOLDER
            )
            ProductInfoPlaceholder(
                stringResource(R.string.warranty_title),
                value = PRODUCT_WARRANTY_PLACEHOLDER
            )
        }
    }
}

@Composable
fun ProductInfoPlaceholder(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        TextPlaceHolder(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.shimmer(dimensionResource(R.dimen.shimmer_corner_radius))
        )
    }
}