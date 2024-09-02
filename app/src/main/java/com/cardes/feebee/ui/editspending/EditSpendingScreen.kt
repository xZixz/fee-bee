package com.cardes.feebee.ui.editspending

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.createspending.SpendingDatePicker
import com.cardes.feebee.ui.design.component.FeeBeeTextInput
import com.cardes.feebee.ui.spendingdetails.DeleteSpendingDialog
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun EditSpendingRoute(
    editSpendingViewModel: EditSpendingViewModel = hiltViewModel(),
    onDoneUpdating: () -> Unit,
    onRemoveSpending: () -> Unit,
) {
    val removeSpendingDialogShowState by editSpendingViewModel.removeSpendingDialogState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.CREATED)
    val editSpendingUiState by editSpendingViewModel.editSpendingScreenUiState.collectAsStateWithLifecycle()
    val showDatePicker by editSpendingViewModel.showDatePicker.collectAsStateWithLifecycle()

    EditSpendingScreen(
        showDatePicker = showDatePicker,
        removeSpendingDialogShowState = removeSpendingDialogShowState,
        onRemoveSpendingClick = editSpendingViewModel::onRemoveSpendingClick,
        removeSpending = { editSpendingViewModel.removeSpending(onRemoveSpending) },
        onDismissRemoveSpendingDialog = editSpendingViewModel::closeRemoveSpendingDialog,
        onSpendingNameChanged = editSpendingViewModel::onSpendingNameChanged,
        editSpendingScreenUiState = editSpendingUiState,
        onCostChanged = editSpendingViewModel::onCostChanged,
        onCalendarClick = editSpendingViewModel::showDatePicker,
        onDatePickerDismiss = editSpendingViewModel::hideDatePicker,
        onDatePickerConfirmed = editSpendingViewModel::onDatePicked,
        onUpdateClick = {
            editSpendingViewModel.update(onDoneUpdating)
        },
    )
}

@Composable
fun EditSpendingScreen(
    onUpdateClick: () -> Unit,
    onDatePickerConfirmed: (Long) -> Unit,
    onDatePickerDismiss: () -> Unit,
    showDatePicker: Boolean,
    editSpendingScreenUiState: EditSpendingScreenUiState,
    onRemoveSpendingClick: () -> Unit,
    removeSpending: () -> Unit,
    onDismissRemoveSpendingDialog: () -> Unit,
    removeSpendingDialogShowState: Boolean,
    onSpendingNameChanged: (String) -> Unit,
    onCostChanged: (String) -> Unit,
    onCalendarClick: () -> Unit,
) {
    if (removeSpendingDialogShowState) {
        DeleteSpendingDialog(
            onDismissRemoveDialog = onDismissRemoveSpendingDialog,
            onRemoveSpending = removeSpending,
        )
    }
    if (showDatePicker) {
        SpendingDatePicker(
            onDatePickerDismiss = {
                onDatePickerDismiss()
            },
            onDatePickerConfirmed = {
                onDatePickerConfirmed(it)
            },
        )
    }
    when (editSpendingScreenUiState) {
        is EditSpendingScreenUiState.Loading -> {}
        is EditSpendingScreenUiState.Success -> {
            BasePage(
                title = stringResource(R.string.edit_spending),
                titleAction = {
                    Icon(
                        modifier = Modifier.clickable {
                            onRemoveSpendingClick()
                        },
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove this spending",
                    )
                },
                content = {
                    FeeBeeTextInput(
                        title = stringResource(id = R.string.description),
                        text = editSpendingScreenUiState.editedSpendingUiState.description,
                        onTextChanged = {
                            onSpendingNameChanged(it)
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FeeBeeTextInput(
                        modifier = Modifier.width(200.dp),
                        title = stringResource(id = R.string.cost),
                        text = editSpendingScreenUiState.editedSpendingUiState.cost,
                        onTextChanged = {
                            onCostChanged(it)
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = stringResource(id = R.string.date))
                    val onCalendarClickModifier = remember {
                        Modifier.clickable { onCalendarClick() }
                    }
                    Row {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = editSpendingScreenUiState.editedSpendingUiState.date,
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Icon(
                            modifier = onCalendarClickModifier,
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date picker",
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            onUpdateClick()
                        },
                    ) {
                        Text(text = stringResource(R.string.update))
                    }
                },
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    heightDp = 400,
    widthDp = 250,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 400,
    widthDp = 250,
)
@Composable
private fun EditSpendingScreenPreview(
    @PreviewParameter(EditSpendingScreenPreviewParameter::class)
    editSpendingScreenUiState: EditSpendingScreenUiState,
) {
    FeeBeeTheme {
        Surface {
            EditSpendingScreen(
                onRemoveSpendingClick = {},
                removeSpending = {},
                removeSpendingDialogShowState = false,
                onDismissRemoveSpendingDialog = {},
                onSpendingNameChanged = {},
                editSpendingScreenUiState = editSpendingScreenUiState,
                onCostChanged = {},
                onCalendarClick = {},
                onDatePickerDismiss = {},
                showDatePicker = false,
                onDatePickerConfirmed = {},
                onUpdateClick = {},
            )
        }
    }
}
