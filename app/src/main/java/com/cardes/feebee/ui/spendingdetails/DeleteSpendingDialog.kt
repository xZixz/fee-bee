package com.cardes.feebee.ui.spendingdetails

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.feebee.R

@Composable
fun DeleteSpendingDialog(
    onDismissRemoveDialog: () -> Unit,
    removeSpending: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRemoveDialog()
        },
        confirmButton = {
            Button(
                onClick = { removeSpending() },
            ) {
                Text(text = stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRemoveDialog() },
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            Text(text = stringResource(R.string.remove_spending_confirm_question))
        },
    )
}
