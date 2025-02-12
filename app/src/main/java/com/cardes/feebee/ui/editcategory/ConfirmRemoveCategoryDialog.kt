package com.cardes.feebee.ui.editcategory

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.feebee.R

@Composable
fun ConfirmRemoveCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    onConfirm()
                },
            ) {
                Text(text = stringResource(R.string.confirm_removing_category_text))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            Text(text = stringResource(R.string.confirm_remove_category_dialog_content))
        },
    )
}
