package com.cardes.feebee.ui.editcategory

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.feebee.R

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
        onRemoveEmoji = viewModel::onRemoveEmoji,
    )
}

@Composable
fun EditCategoryScreen(
    modifier: Modifier = Modifier,
    fetchingCategoryUiState: FetchingCategoryUiState,
    onEditingCategoryNameChanged: (String) -> Unit,
    onRemoveEmoji: () -> Unit,
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    textDecoration = TextDecoration.Underline,
                    text = stringResource(id = R.string.category),
                )
                Spacer(modifier = Modifier.height(30.dp))

                EmojiSelectButton(
                    onEmojiPicked = onEmojiPicked,
                    onRemoveEmoji = onRemoveEmoji,
                    emoji = fetchingCategoryUiState.editCategoryUiState.emoji,
                )
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
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = fetchingCategoryUiState.editCategoryUiState.categoryName,
                    style = MaterialTheme.typography.displayLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                SmallFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = CircleShape,
                    onClick = { showCategoryNameEditDialog = true },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(15.dp),
                        imageVector = Icons.Default.ModeEdit,
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
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    onClick = { showCategoryRemoveDialog = true },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove category",
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun EmojiSelectButton(
    onEmojiPicked: (String) -> Unit,
    onRemoveEmoji: () -> Unit,
    emoji: String,
) {
    var showEmojisPopup by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { showEmojisPopup = true },
        ),
    ) {
        Crossfade(
            targetState = emoji.isNotBlank(),
        ) { isIconVisible ->
            when (isIconVisible) {
                true -> {
                    Box {
                        Text(
                            modifier = Modifier.size(75.dp),
                            fontSize = 57.sp,
                            text = emoji,
                            textAlign = TextAlign.Center,
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .shadow(8.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable { onRemoveEmoji() },
                                imageVector = Icons.Default.RemoveCircle,
                                tint = Color.Gray,
                                contentDescription = "Remove emoji",
                            )
                        }
                    }
                }

                false -> {
                    Icon(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(10.dp),
                        imageVector = Icons.Default.AddReaction,
                        contentDescription = "Empty emoji",
                        tint = Color.Gray,
                    )
                }
            }
        }
        if (showEmojisPopup) {
            EmojiPicker(
                onDismiss = { showEmojisPopup = false },
                onEmojiPicked = onEmojiPicked,
            )
        }
    }
}

@Composable
private fun rememberPopupPositionProvider(): PopupPositionProvider {
    val currentDensity = LocalDensity.current
    return remember {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset {
                val x = anchorBounds.left + anchorBounds.width / 2 - popupContentSize.width / 2
                val padding = with(currentDensity) { 5.dp.roundToPx() }
                return IntOffset(x = x, y = anchorBounds.top + anchorBounds.height + padding)
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
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
                onRemoveEmoji = {},
            )
        }
    }
}
