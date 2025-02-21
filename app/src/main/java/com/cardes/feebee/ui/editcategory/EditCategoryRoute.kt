package com.cardes.feebee.ui.editcategory

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.ui.common.emojis
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
                Spacer(modifier = Modifier.height(10.dp))
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

                Spacer(modifier = Modifier.height(50.dp))
                EmojiSelectButton(
                    onEmojiPicked = onEmojiPicked,
                    onRemoveEmoji = onRemoveEmoji,
                    emoji = fetchingCategoryUiState.editCategoryUiState.emoji,
                )

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
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when (emoji.isBlank()) {
                true -> {
                    Icon(
                        modifier = Modifier
                            .size(65.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = CircleShape,
                            ).padding(10.dp),
                        imageVector = Icons.Default.AddReaction,
                        contentDescription = "Empty emoji",
                        tint = Color.Gray,
                    )
                }

                false -> {
                    Text(
                        modifier = Modifier.size(65.dp),
                        fontSize = 57.sp,
                        text = emoji,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                AnimatedVisibility(visible = emoji.isNotBlank()) {
                    SmallFloatingActionButton(
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        onClick = { onRemoveEmoji.invoke() },
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Remove emoji",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                    }
                }
                SmallFloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { showEmojisPopup = true },
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.AddReaction,
                        contentDescription = "Remove emoji",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    )

                    if (showEmojisPopup) {
                        EmojiPopup(
                            onDismiss = { showEmojisPopup = false },
                            onEmojiPicked = onEmojiPicked,
                        )
                    }
                }
            }
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

@Composable
fun EmojiPopup(
    onDismiss: () -> Unit,
    onEmojiPicked: (String) -> Unit,
    popupPositionProvider: PopupPositionProvider = rememberPopupPositionProvider(),
) {
    Popup(
        onDismissRequest = onDismiss,
        popupPositionProvider = popupPositionProvider,
    ) {
        Card(
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(10.dp),
                columns = GridCells.FixedSize(size = 45.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(emojis) { emoji ->
                    Text(
                        modifier = Modifier
//                            .size(45.dp)
                            .clickable {
                                onDismiss()
                                onEmojiPicked(emoji)
                            },
                        text = emoji,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                    )
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
                onRemoveEmoji = {},
            )
        }
    }
}
