package com.example.productsStore.presentation.ui.component.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsstrore.R

@Composable
fun SmartToastHost(
    manager: ToastManager,
    modifier: Modifier = Modifier
) {
    val currentToast by manager.currentToast.collectAsStateWithLifecycle()
    val toastIsVisible by manager.toastIsVisible.collectAsStateWithLifecycle()

    AnimatedVisibility(
        visible = toastIsVisible,
        enter = scaleIn(spring(stiffness = Spring.StiffnessMediumLow)) + fadeIn(tween(200)),
        exit = scaleOut(spring(stiffness = Spring.StiffnessMediumLow)) + fadeOut(tween(200)),
        modifier = modifier
    ) {
        currentToast?.let { smartToastModel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.large_padding))
                    .shadow(
                        dimensionResource(R.dimen.smart_toast_elevation),
                        MaterialTheme.shapes.medium
                    )
                    .clickable {
                        smartToastModel.onClick?.invoke()
                        manager.dismiss()
                    },
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseSurface)
            ) {
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.large_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = smartToastModel.message,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}