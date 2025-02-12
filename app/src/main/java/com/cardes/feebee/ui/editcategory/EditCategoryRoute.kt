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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.EmojisPopup
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun EditCategoryRoute(
    modifier: Modifier = Modifier,
    viewModel: EditCategoryViewModel = hiltViewModel(),
    onFinishRemovingCategory: () -> Unit,
) {
    val fetchingCategoryUiState by viewModel.fetchingCategoryUiState.collectAsStateWithLifecycle()

    EditCategoryScreen(
        modifier = modifier,
        fetchingCategoryUiState = fetchingCategoryUiState,
        onConfirmUpdateCategoryName = viewModel::onConfirmUpdateCategoryName,
        onEditingCategoryNameChanged = viewModel::onEditingCategoryNameChanged,
        onConfirmRemoveCategory = {
            viewModel.removeCategory(onFinishRemovingCategory)
        },
        onEmojiPicked = viewModel::onEmojiPicked,
    )
}

@Composable
fun EditCategoryScreen(
    modifier: Modifier = Modifier,
    fetchingCategoryUiState: FetchingCategoryUiState,
    onEditingCategoryNameChanged: (String) -> Unit,
    onConfirmUpdateCategoryName: (String) -> Unit,
    onConfirmRemoveCategory: () -> Unit,
    onEmojiPicked: (String) -> Unit,
) {
    when (fetchingCategoryUiState) {
        FetchingCategoryUiState.Loading -> {}
        is FetchingCategoryUiState.Success -> {
            Column(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 10.dp),
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    // Edit category's name
                    var showCategoryNameEditDialog by remember { mutableStateOf(false) }

                    if (showCategoryNameEditDialog) {
                        EditCategoryNameDialog(
                            onDialogDismiss = { showCategoryNameEditDialog = false },
                            onEditingNameChanged = onEditingCategoryNameChanged,
                            onConfirm = onConfirmUpdateCategoryName,
                            editingName = fetchingCategoryUiState.editCategoryUiState.editingCategoryName,
                        )
                    }
                    Text(
                        text = fetchingCategoryUiState.editCategoryUiState.categoryName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier.clickable { showCategoryNameEditDialog = true },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit category's name",
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))

                // Remove category
                var showCategoryRemoveDialog by remember { mutableStateOf(false) }
                if (showCategoryRemoveDialog) {
                    ConfirmRemoveCategoryDialog(
                        onDismiss = { showCategoryRemoveDialog = false },
                        onConfirm = onConfirmRemoveCategory,
                    )
                }
                Button(
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showCategoryRemoveDialog = true },
                ) {
                    Text(text = stringResource(R.string.remove_this_category))
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
                onEditingCategoryNameChanged = {},
                onConfirmUpdateCategoryName = {},
                onConfirmRemoveCategory = {},
                onEmojiPicked = {},
            )
        }
    }
}
