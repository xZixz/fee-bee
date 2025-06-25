package com.cardes.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.cardes.designsystem.R
import com.cardes.designsystem.common.spendingDateDisplayFormat
import java.text.DateFormat
import java.util.Calendar

@Composable
fun FeeBeeDateInput(
    time: Long,
    modifier: Modifier = Modifier,
    dateFormat: DateFormat = remember { spendingDateDisplayFormat },
    onDatePicked: (Long) -> Unit,
    lastSelectableDate: String? = null,
) {
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = modifier) {
        FeeBeeTextInput(
            title = stringResource(id = R.string.date),
            text = remember(time) { dateFormat.format(time) },
            onTextChanged = {},
            readOnly = true,
            modifier = Modifier
                .focusRequester(focusRequester),
        )
        Box(
            Modifier
                .matchParentSize()
                .clickable(
                    onClick = {
                        showDatePickerDialog = true
                        focusRequester.requestFocus()
                    },
                    interactionSource = interactionSource,
                    indication = null,
                ),
        ) { }
    }

    if (showDatePickerDialog) {
        val lastSelectableDateTime = remember(lastSelectableDate) {
            if (lastSelectableDate != null) {
                dateFormat.parse(lastSelectableDate)?.time ?: 0L
            } else {
                Calendar.getInstance().timeInMillis
            }
        }
        FeeBeeDatePicker(
            onDatePickerDismiss = {
                showDatePickerDialog = false
            },
            onDatePickerConfirmed = { time ->
                showDatePickerDialog = false
                onDatePicked(time)
            },
            initialDate = time,
            lastSelectableDate = lastSelectableDateTime,
        )
    }
}
