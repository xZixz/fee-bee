package com.cardes.feebee.ui.spendingdetails

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.spendingslist.CategoryChip
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun SpendingDetailsRoute(
    spendingDetailsViewModel: SpendingDetailsViewModel = hiltViewModel(),
    onSpendingRemoved: () -> Unit,
) {
    val spendingUiState by spendingDetailsViewModel.spendingUiState.collectAsStateWithLifecycle()
    val removeSpendingDialogState by spendingDetailsViewModel.removeSpendingDialogState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.CREATED)

    SpendingDetailsScreen(
        removeSpendingDialogState = removeSpendingDialogState,
        spendingUiState = spendingUiState,
        onDismissRemoveDialog = spendingDetailsViewModel::closeRemoveSpendingDialog,
        onRemoveSpendingClick = spendingDetailsViewModel::onRemoveSpendingClick,
        removeSpending = {
            spendingDetailsViewModel.removeSpending(onSpendingRemoved)
        },
        onEditClick = spendingDetailsViewModel::onEditClick,
        onExitEditModeClick = spendingDetailsViewModel::exitEditMode,
    )
}

@Composable
fun SpendingDetailsScreen(
    spendingUiState: SpendingUiState,
    removeSpendingDialogState: Boolean,
    onDismissRemoveDialog: () -> Unit,
    onRemoveSpendingClick: () -> Unit,
    onExitEditModeClick: () -> Unit,
    onEditClick: () -> Unit,
    removeSpending: () -> Unit,
) {
    if (removeSpendingDialogState) {
        DeleteSpendingDialog(
            onDismissRemoveDialog = onDismissRemoveDialog,
            removeSpending = removeSpending,
        )
    }
    when (spendingUiState) {
        is SpendingUiState.Loading -> {}
        is SpendingUiState.Success -> {
            SpendingDetails(
                spendingUiState = spendingUiState,
                onEditClick = onEditClick,
                onExitEditModeClick = onExitEditModeClick,
                onRemoveSpendingClick = onRemoveSpendingClick,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpendingDetails(
    spendingUiState: SpendingUiState.Success,
    onRemoveSpendingClick: () -> Unit,
    onExitEditModeClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
) {
    BasePage(
        modifier = modifier,
        title = spendingUiState.description,
        titleAction = {
            when (spendingUiState.viewMode) {
                SpendingUiState.ViewMode.VIEW_ONLY -> {
                    Icon(
                        modifier = Modifier.clickable {
                            onEditClick()
                        },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Enter Edit mode",
                    )
                }

                SpendingUiState.ViewMode.EDIT -> {
                    Icon(
                        modifier = Modifier.clickable {
                            onExitEditModeClick()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to View mode",
                    )
                }
            }
        },
    ) {
        Text(
            text = "${spendingUiState.cost} $",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = spendingUiState.date,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            spendingUiState.categories.forEach { category ->
                CategoryChip(text = category.name)
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        if (spendingUiState.viewMode == SpendingUiState.ViewMode.EDIT) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                ),
                onClick = {
                    onRemoveSpendingClick()
                },
            ) {
                Text(
                    text = stringResource(R.string.remove_this_spending),
                    color = MaterialTheme.colorScheme.error,
                )
            }
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
private fun SpendingDetailsPreview(
    @PreviewParameter(SpendingDetailsUiStatePreviewParameter::class)
    spendingUiState: SpendingUiState.Success,
) {
    FeeBeeTheme {
        Surface {
            SpendingDetails(
                spendingUiState = spendingUiState,
                onRemoveSpendingClick = {},
                onEditClick = {},
                onExitEditModeClick = {},
            )
        }
    }
}
