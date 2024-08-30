package com.cardes.feebee.ui.createspending

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.design.component.FeeBeeTextInput
import com.cardes.feebee.ui.theme.FeeBeeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CreateSpendingRoute(
    onNavUp: () -> Unit,
    createSpendingViewModel: CreateSpendingViewModel = hiltViewModel(),
) {
    val createSpendingUiState by createSpendingViewModel.createSpendingUiState.collectAsStateWithLifecycle()
    val showDatePickerDialog by createSpendingViewModel.showDatePickerDialog.collectAsStateWithLifecycle()
    val showAddCategoryDialog by createSpendingViewModel.showAddCategoryDialog.collectAsStateWithLifecycle()

    CreateSpendingScreen(
        createSpendingUiState = createSpendingUiState,
        showDatePickerDialog = showDatePickerDialog,
        showAddCategoryDialog = showAddCategoryDialog,
        onDescriptionChanged = createSpendingViewModel::onDescriptionChanged,
        onCostChanged = createSpendingViewModel::onCostChanged,
        onCreateSpendingClicked = {
            createSpendingViewModel.createSpending(onFinishedCreating = onNavUp)
        },
        onCalendarClick = createSpendingViewModel::showDatePickerDialog,
        onDatePickerDismiss = createSpendingViewModel::hideDatePickerDialog,
        onDatePickerConfirmed = createSpendingViewModel::onDatePicked,
        onCategoryClick = createSpendingViewModel::onCategoryClick,
        onAddCategoryClick = createSpendingViewModel::onAddCategoryClick,
        onAddCategoryDismiss = createSpendingViewModel::onAddCategoryDialogDismiss,
        onAddCategory = createSpendingViewModel::onAddCategory,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateSpendingScreen(
    createSpendingUiState: CreateSpendingUiState,
    showDatePickerDialog: Boolean,
    showAddCategoryDialog: Boolean,
    onDescriptionChanged: (String) -> Unit,
    onCostChanged: (String) -> Unit,
    onCreateSpendingClicked: () -> Unit,
    onCalendarClick: () -> Unit,
    onDatePickerDismiss: () -> Unit,
    onDatePickerConfirmed: (Long) -> Unit,
    onCategoryClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
    onAddCategoryDismiss: () -> Unit,
    onAddCategory: (String) -> Unit,
) {
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

    BasePage(
        title = stringResource(R.string.create_a_spending),
        content = {
            Column {
                FeeBeeTextInput(
                    title = stringResource(id = R.string.description),
                    text = createSpendingUiState.description,
                    onTextChanged = {
                        onDescriptionChanged(it)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                FeeBeeTextInput(
                    modifier = Modifier.width(200.dp),
                    title = stringResource(id = R.string.cost),
                    text = createSpendingUiState.amount,
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
                        text = createSpendingUiState.date,
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        modifier = onCalendarClickModifier,
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date picker",
                    )
                }
                // Categories
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = stringResource(R.string.categories))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    createSpendingUiState.categories.forEach { category ->
                        FilterChip(
                            selected = category.id in createSpendingUiState.selectedCategoryIds,
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
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onCreateSpendingClicked()
                    },
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = stringResource(R.string.create))
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
private fun CreateSpendingScreenPreview(
    @PreviewParameter(CreateSpendingUiStatePreviewParameter::class)
    createSpendingUiState: CreateSpendingUiState,
) {
    FeeBeeTheme {
        Surface {
            CreateSpendingScreen(
                createSpendingUiState = createSpendingUiState,
                showDatePickerDialog = false,
                onDescriptionChanged = {},
                onCostChanged = {},
                onCreateSpendingClicked = {},
                onCalendarClick = {},
                onDatePickerDismiss = {},
                onDatePickerConfirmed = {},
                onCategoryClick = {},
                onAddCategoryClick = {},
                showAddCategoryDialog = false,
                onAddCategoryDismiss = {},
                onAddCategory = {},
            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction): Boolean = true
}
