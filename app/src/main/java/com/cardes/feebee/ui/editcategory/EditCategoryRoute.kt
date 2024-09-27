package com.cardes.feebee.ui.editcategory

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun EditCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: EditCategoryViewModel = hiltViewModel(),
    onFinishRemovingCategory: () -> Unit,
) {
    val fetchingCategoryUiState by viewModel.fetchingCategoryUiState.collectAsStateWithLifecycle()
    val showCategoryNameEditDialog by viewModel.showCategoryNameEditDialog.collectAsStateWithLifecycle()
    val showCategoryRemoveDialog by viewModel.showRemoveCategoryDialog.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.CREATED)

    EditCategoryScreen(
        modifier = modifier,
        showCategoryNameEditDialog = showCategoryNameEditDialog,
        fetchingCategoryUiState = fetchingCategoryUiState,
        onCategoryNameEditClick = viewModel::onCategoryNameEditClick,
        onConfirmUpdateCategoryName = viewModel::onConfirmUpdateCategoryName,
        dismissEditCategoryNameDialog = viewModel::dismissEditCategoryNameDialog,
        onEditingCategoryNameChanged = viewModel::onEditingCategoryNameChanged,
        onRemoveCategoryButtonClick = viewModel::onCategoryRemoveClick,
        showCategoryRemoveDialog = showCategoryRemoveDialog,
        onConfirmRemoveCategory = {
            viewModel.removeCategory(onFinishRemovingCategory)
        },
        onDismissRemoveCategoryDialog = viewModel::dismissCategoryRemoveDialog,
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
    onRemoveCategoryButtonClick: () -> Unit,
    showCategoryRemoveDialog: Boolean,
    onConfirmRemoveCategory: () -> Unit,
    onDismissRemoveCategoryDialog: () -> Unit,
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
            if (showCategoryRemoveDialog) {
                ConfirmRemoveCategoryDialog(
                    onDismiss = onDismissRemoveCategoryDialog,
                    onConfirm = onConfirmRemoveCategory,
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
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onRemoveCategoryButtonClick()
                    },
                ) {
                    Text(text = "Remove this Category")
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Composable
fun EditCategoryPreview(
    @PreviewParameter(EditCategoryPreviewParameter::class) fetchingCategoryUiState: FetchingCategoryUiState,
) {
    FeeBeeTheme {
        Surface {
            EditCategoryScreen(
                fetchingCategoryUiState = fetchingCategoryUiState,
                dismissEditCategoryNameDialog = {},
                onCategoryNameEditClick = {},
                showCategoryNameEditDialog = false,
                onEditingCategoryNameChanged = {},
                onConfirmUpdateCategoryName = {},
                onRemoveCategoryButtonClick = {},
                showCategoryRemoveDialog = false,
                onConfirmRemoveCategory = {},
                onDismissRemoveCategoryDialog = {},
            )
        }
    }
}
