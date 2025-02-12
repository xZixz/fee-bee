package com.cardes.feebee.ui.editcategory

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.feebee.R

@Composable
fun EditCategoryNameDialog(
    onDialogDismiss: () -> Unit,
    onEditingNameChanged: (String) -> Unit,
    onConfirm: (String) -> Unit,
    editingName: String,
) {
    AlertDialog(
        onDismissRequest = onDialogDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onDialogDismiss()
                    onConfirm(editingName)
                },
            ) {
                Text(text = stringResource(R.string.change))
            }
        },
        dismissButton = {
            Button(onClick = onDialogDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            TextField(
                value = editingName,
                onValueChange = onEditingNameChanged,
            )
        },
    )
}
