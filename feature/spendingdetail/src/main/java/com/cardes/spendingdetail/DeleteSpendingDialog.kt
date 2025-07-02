package com.cardes.spendingdetail

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.ui.R as uiR

@Composable
fun DeleteSpendingDialog(
    onDismissRemoveDialog: () -> Unit,
    onRemoveSpending: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRemoveDialog()
        },
        confirmButton = {
            Button(
                onClick = { onRemoveSpending() },
            ) {
                Text(text = stringResource(id = uiR.string.yes))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRemoveDialog() },
            ) {
                Text(text = stringResource(id = uiR.string.cancel))
            }
        },
        text = {
            Text(text = stringResource(R.string.remove_spending_confirm_question))
        },
    )
}
