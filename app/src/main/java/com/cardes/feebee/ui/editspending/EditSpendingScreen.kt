package com.cardes.feebee.ui.editspending

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.design.component.FeeBeeTextInput
import com.cardes.feebee.ui.spendingdetails.DeleteSpendingDialog
import com.cardes.feebee.ui.theme.FeeBeeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun EditSpendingRoute(
    onNavUp: () -> Unit,
    onSpendingRemoved: () -> Unit,
    editSpendingViewModel: EditSpendingViewModel = hiltViewModel(),
) {
    val editSpendingUiState by editSpendingViewModel.createSpendingUiState.collectAsStateWithLifecycle()
    val showDatePickerDialog by editSpendingViewModel.showDatePickerDialog.collectAsStateWithLifecycle()
    val showAddCategoryDialog by editSpendingViewModel.showAddCategoryDialog.collectAsStateWithLifecycle()
    val removeSpendingDialogShowState by editSpendingViewModel.removeSpendingDialogState.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.CREATED,
    )

    EditSpendingScreen(
        removeSpendingDialogShowState = removeSpendingDialogShowState,
        editMode = editSpendingViewModel.editMode,
        editSpendingUiState = editSpendingUiState,
        showDatePickerDialog = showDatePickerDialog,
        showAddCategoryDialog = showAddCategoryDialog,
        onDescriptionChanged = editSpendingViewModel::onDescriptionChanged,
        onCostChanged = editSpendingViewModel::onCostChanged,
        onCtaButtonClick = {
            editSpendingViewModel.onCtaButtonClick(onFinishedCreating = onNavUp)
        },
        onCalendarClick = editSpendingViewModel::showDatePickerDialog,
        onDatePickerDismiss = editSpendingViewModel::hideDatePickerDialog,
        onDatePickerConfirmed = editSpendingViewModel::onDatePicked,
        onCategoryClick = editSpendingViewModel::onCategoryClick,
        onAddCategoryClick = editSpendingViewModel::onAddCategoryClick,
        onAddCategoryDismiss = editSpendingViewModel::onAddCategoryDialogDismiss,
        onAddCategory = editSpendingViewModel::onAddCategory,
        onRemoveSpendingClick = editSpendingViewModel::onRemoveSpendingClick,
        onDismissRemoveSpendingDialog = editSpendingViewModel::closeRemoveSpendingDialog,
        removeSpending = {
            editSpendingViewModel.removeSpending(onSpendingRemoved)
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditSpendingScreen(
    removeSpendingDialogShowState: Boolean,
    onDismissRemoveSpendingDialog: () -> Unit,
    removeSpending: () -> Unit,
    editMode: EditMode,
    editSpendingUiState: EditSpendingUiState,
    showDatePickerDialog: Boolean,
    showAddCategoryDialog: Boolean,
    onDescriptionChanged: (String) -> Unit,
    onCostChanged: (String) -> Unit,
    onCtaButtonClick: () -> Unit,
    onRemoveSpendingClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onDatePickerDismiss: () -> Unit,
    onDatePickerConfirmed: (Long) -> Unit,
    onCategoryClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
    onAddCategoryDismiss: () -> Unit,
    onAddCategory: (String) -> Unit,
) {
    if (removeSpendingDialogShowState) {
        DeleteSpendingDialog(
            onDismissRemoveDialog = onDismissRemoveSpendingDialog,
            onRemoveSpending = removeSpending,
        )
    }

    if (showDatePickerDialog) {
        SpendingDatePicker(
            onDatePickerDismiss = onDatePickerDismiss,
            onDatePickerConfirmed = onDatePickerConfirmed,
        )
    }

    if (showAddCategoryDialog) {
        CreateCategoryDialog(
            onDismiss = { onAddCategoryDismiss() },
            onConfirm = { categoryName -> onAddCategory(categoryName) },
        )
    }

    val screenTitleStringResourceId = remember {
        when (editMode) {
            EditMode.NEW -> R.string.new_spending
            EditMode.EDIT -> R.string.edit_spending
        }
    }

    BasePage(
        title = stringResource(screenTitleStringResourceId),
        titleAction = {
            if (editMode == EditMode.EDIT) {
                Icon(
                    modifier = Modifier.clickable {
                        onRemoveSpendingClick()
                    },
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove this spending",
                )
            }
        },
        content = {
            Column {
                FeeBeeTextInput(
                    title = stringResource(id = R.string.description),
                    text = editSpendingUiState.description,
                    onTextChanged = {
                        onDescriptionChanged(it)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                FeeBeeTextInput(
                    modifier = Modifier.width(200.dp),
                    title = stringResource(id = R.string.cost),
                    text = editSpendingUiState.amount,
                    onTextChanged = { amount ->
                        onCostChanged(amount)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                val focusRequester = remember { FocusRequester() }
                val interactionSource = remember { MutableInteractionSource() }
                Box {
                    FeeBeeTextInput(
                        title = stringResource(id = R.string.date),
                        text = editSpendingUiState.date,
                        onTextChanged = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    )
                    Box(
                        Modifier
                            .matchParentSize()
                            .clickable(
                                onClick = {
                                    onCalendarClick()
                                    focusRequester.requestFocus()
                                },
                                interactionSource = interactionSource,
                                indication = null,
                            ),
                    ) { }
                }
                // Categories
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = stringResource(R.string.categories))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    editSpendingUiState.categories.forEach { category ->
                        FilterChip(
                            selected = category.id in editSpendingUiState.selectedCategoryIds,
                            onClick = {
                                onCategoryClick(category.id)
                            },
                            shape = RoundedCornerShape(50),
                            label = {
                                Text(text = category.name)
                            },
                            interactionSource = NoRippleInteractionSource(),
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(30.dp),
                        onClick = {
                            onAddCategoryClick()
                        },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add category",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                val ctaStringResourceId = when (editMode) {
                    EditMode.NEW -> R.string.create
                    EditMode.EDIT -> R.string.update
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onCtaButtonClick()
                    },
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = stringResource(ctaStringResourceId))
                }
            }
        },
    )
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 250,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 250,
)
@Composable
private fun EditSpendingScreenPreview(
    @PreviewParameter(EditSpendingUiStatePreviewParameter::class)
    editSpendingPreviewParam: EditSpendingPreviewParam,
) {
    FeeBeeTheme {
        Surface {
            EditSpendingScreen(
                editMode = editSpendingPreviewParam.editMode,
                editSpendingUiState = editSpendingPreviewParam.editSpendingUiState,
                showDatePickerDialog = false,
                onDescriptionChanged = {},
                onCostChanged = {},
                onCtaButtonClick = {},
                onCalendarClick = {},
                onDatePickerDismiss = {},
                onDatePickerConfirmed = {},
                onCategoryClick = {},
                onAddCategoryClick = {},
                showAddCategoryDialog = false,
                onAddCategoryDismiss = {},
                onAddCategory = {},
                onRemoveSpendingClick = {},
                onDismissRemoveSpendingDialog = {},
                removeSpending = {},
                removeSpendingDialogShowState = false,
            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction): Boolean = true
}
