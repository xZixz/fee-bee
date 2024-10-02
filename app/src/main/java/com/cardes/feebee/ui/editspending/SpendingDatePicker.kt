package com.cardes.feebee.ui.editspending

import android.icu.util.Calendar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cardes.feebee.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingDatePicker(
    onDatePickerDismiss: () -> Unit,
    onDatePickerConfirmed: (Long) -> Unit,
) {
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis < Calendar.getInstance().timeInMillis
        },
    )

    DatePickerDialog(
        onDismissRequest = {
            onDatePickerDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    onDatePickerConfirmed(dateState.selectedDateMillis ?: 0)
                },
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDatePickerDismiss()
                },
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
    ) {
        DatePicker(state = dateState)
    }
}
