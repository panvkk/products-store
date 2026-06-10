package com.example.productsStore.presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.productsstrore.R

@Composable
fun CartHintDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.cart_hint_title)) },
        text = { Text(stringResource(R.string.cart_hint_text)) },
        confirmButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cart_hint_confirm_button)) } }
    )
}