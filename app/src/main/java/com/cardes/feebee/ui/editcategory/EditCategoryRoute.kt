package com.cardes.feebee.ui.editcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: EditCategoryViewModel = hiltViewModel(),
) {
    val fetchingCategoryUiState by viewModel.fetchingCategoryUiState.collectAsStateWithLifecycle()
    val showCategoryNameEditDialog by viewModel.showCategoryNameEditDialog.collectAsStateWithLifecycle()

    EditCategoryScreen(
        modifier = modifier,
        showCategoryNameEditDialog = showCategoryNameEditDialog,
        fetchingCategoryUiState = fetchingCategoryUiState,
        onCategoryNameEditClick = viewModel::onCategoryNameEditClick,
        onConfirmUpdateCategoryName = viewModel::onConfirmUpdateCategoryName,
        dismissEditCategoryNameDialog = viewModel::dismissEditCategoryNameDialog,
        onEditingCategoryNameChanged = viewModel::onEditingCategoryNameChanged,
    )
}

@Composable
fun EditCategoryScreen(
    modifier: Modifier = Modifier,
    fetchingCategoryUiState: FetchingCategoryUiState,
    dismissEditCategoryNameDialog: () -> Unit,
    onCategoryNameEditClick: () -> Unit,
    showCategoryNameEditDialog: Boolean,
    onEditingCategoryNameChanged: (String) -> Unit,
    onConfirmUpdateCategoryName: (String) -> Unit,
) {
    when (fetchingCategoryUiState) {
        FetchingCategoryUiState.Loading -> {}
        is FetchingCategoryUiState.Success -> {
            if (showCategoryNameEditDialog) {
                EditCategoryNameDialog(
                    onDialogDismiss = dismissEditCategoryNameDialog,
                    onEditingNameChanged = onEditingCategoryNameChanged,
                    onConfirm = onConfirmUpdateCategoryName,
                    editingName = fetchingCategoryUiState.editCategoryUiState.editingCategoryName,
                )
            }
            Column(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 10.dp),
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = fetchingCategoryUiState.editCategoryUiState.categoryName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier.clickable {
                            onCategoryNameEditClick()
                        },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit category's name",
                    )
                }
            }
        }
    }
}
