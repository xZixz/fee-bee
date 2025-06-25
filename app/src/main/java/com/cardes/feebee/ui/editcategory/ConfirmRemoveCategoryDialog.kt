package com.cardes.feebee.ui.editcategory

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cardes.designsystem.theme.FeeBeeTheme
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ),
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ConfirmRemoveCategoryDialogPreview() {
    FeeBeeTheme {
        ConfirmRemoveCategoryDialog(
            onDismiss = {},
            onConfirm = {},
        )
    }
}
