package com.cardes.feebee.ui.editcategory

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.DarkModePreview
import com.cardes.feebee.ui.theme.FeeBeeTheme

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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            ) {
                Text(text = stringResource(R.string.update))
            }
        },
        dismissButton = {
            Button(onClick = onDialogDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            OutlinedTextField(
                value = editingName,
                onValueChange = onEditingNameChanged,
                label = {
                    Text(text = stringResource(R.string.category_name))
                },
            )
        },
    )
}

@DarkModePreview
@Composable
private fun EditCategoryNameDialogPreview() {
    FeeBeeTheme {
        EditCategoryNameDialog(
            onDialogDismiss = {},
            onEditingNameChanged = {},
            onConfirm = {},
            editingName = "Nice",
        )
    }
}
