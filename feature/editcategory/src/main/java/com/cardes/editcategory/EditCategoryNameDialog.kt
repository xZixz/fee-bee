package com.cardes.editcategory

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.ui.DarkModePreview
import com.cardes.ui.R as uiR

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
                Text(text = stringResource(uiR.string.update))
            }
        },
        dismissButton = {
            Button(onClick = onDialogDismiss) {
                Text(text = stringResource(id = uiR.string.cancel))
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
