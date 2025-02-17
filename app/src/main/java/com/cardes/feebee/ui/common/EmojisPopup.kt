package com.cardes.feebee.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.cardes.feebee.ui.theme.FeeBeeTheme

val emojis = listOf(
    // Housing & Utilities
    "🏡",
    "🏠",
    "💡",
    "🚰",
    "🔥",
    // Groceries & Essentials
    "🛒",
    "🍞",
    "🥛",
    "🥦",
    "🍎",
    // Transportation
    "⛽",
    "🚗",
    "🚌",
    "🚆",
    "🚕",
    // Dining & Restaurants
    "🍽️",
    "🍕",
    "🍔",
    "🥤",
    "🍣",
    // Shopping & Personal Care
    "🛍️",
    "👗",
    "👞",
    "💄",
    "💎",
    // Health & Medical
    "🏥",
    "💊",
    "🩺",
    "🦷",
    "🤕",
    // Entertainment & Hobbies
    "🎟️",
    "🎮",
    "📚",
    "🎸",
    "🍿",
    // Education & Learning
    "🎓",
    "📖",
    "✏️",
    "🖥️",
    "🧑‍🏫",
    // Work & Business
    "💼",
    "🖊️",
    "📊",
    "🖥️",
    "📞",
    // Savings & Financials
    "💰",
    "💵",
    "💳",
    "🏦",
    "📉",
    // Cleaning & Household
    "🧽",
    "🪣",
    "🧹",
    "🧴",
    "🛁",
    "🧼",
    "🫧",
    "🚽",
    "🧺",
)

@Composable
fun EmojisPopup(
    onEmojiPicked: (String) -> Unit,
    onDismiss: () -> Unit,
    alignment: Alignment = Alignment.Center,
) {
    Popup(
        onDismissRequest = onDismiss,
        alignment = alignment,
    ) {
        Card(
            modifier = Modifier
                .height(160.dp)
                .width(290.dp),
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(5.dp),
                columns = GridCells.FixedSize(size = 25.dp),
            ) {
                items(emojis) { emoji ->
                    Text(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onDismiss()
                                onEmojiPicked(emoji)
                            },
                        text = emoji,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EmojiPopupPreview() {
    FeeBeeTheme {
        EmojisPopup(
            onEmojiPicked = {},
            onDismiss = {},
        )
    }
}
