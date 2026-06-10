package com.cardes.editcategory.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.ui.shared.DarkModePreview

val foodAndDrinkEmojis = listOf(
    "🍔",
    "🍕",
    "🥗",
    "🍣",
    "🍦",
    "🍺",
)

val transportationEmojis = listOf(
    "🚗",
    "🚌",
    "🚉",
    "🚕",
    "🚲",
)

val entertainmentEmojis = listOf(
    "🎮",
    "🎬",
    "🎤",
    "🎧",
    "🎡",
)

val healthAndFitnessEmojis = listOf(
    "🏋️‍♀️",
    "💪",
    "🧘‍♂️",
    "🏃‍♂️",
    "🏊‍♀️",
)

val shoppingEmojis = listOf(
    "🛍️",
    "👗",
    "👠",
    "🧢",
    "🧳",
)

val housingEmojis = listOf(
    "🏥",
    "🏢",
    "🏠",
    "🧹",
    "🧽",
    "🧴",
)

val gardenEmojis = listOf(
    "🌱",
    "🌻",
    "🌼",
    "🌳",
    "🌺",
    "🌷",
    "🌾",
    "🍃",
    "🌿",
    "🪴",
)

val billsAndUtilitiesEmojis = listOf(
    "💡",
    "📱",
    "💳",
    "💵",
    "🧾",
)

val travelEmojis = listOf(
    "✈️",
    "🌍",
    "🚢",
    "🗺️",
    "🏖️",
)

val giftsEmojis = listOf(
    "🎁",
    "💐",
    "🎉",
    "🎂",
    "🧸",
)

val educationEmojis = listOf(
    "📚",
    "💻",
    "🎓",
    "🖋️",
    "📝",
)

val personalCareEmojis = listOf(
    "💅",
    "🧴",
    "🧖‍♀️",
    "🛁",
    "🧼",
)

val petsEmojis = listOf(
    "🐶",
    "🐱",
    "🐾",
    "🐰",
    "🦜",
)

val miscellaneousEmojis = listOf(
    "🌈",
    "🔧",
    "💡",
    "🛠️",
    "🔑",
)

data class EmojiCategory(
    val name: String,
    val emojis: List<String>,
)

private val emojis = listOf(
    EmojiCategory(
        name = "Food & Drinks",
        emojis = foodAndDrinkEmojis,
    ),
    EmojiCategory(
        name = "Transportations",
        emojis = transportationEmojis,
    ),
    EmojiCategory(
        name = "Entertainment",
        emojis = entertainmentEmojis,
    ),
    EmojiCategory(
        name = "Health & Fitness",
        emojis = healthAndFitnessEmojis,
    ),
    EmojiCategory(
        name = "Shopping",
        emojis = shoppingEmojis,
    ),
    EmojiCategory(
        name = "Housing",
        emojis = housingEmojis,
    ),
    EmojiCategory(
        name = "Garden",
        emojis = gardenEmojis,
    ),
    EmojiCategory(
        name = "Utilities",
        emojis = billsAndUtilitiesEmojis,
    ),
    EmojiCategory(
        name = "Travel",
        emojis = travelEmojis,
    ),
    EmojiCategory(
        name = "Gifts",
        emojis = giftsEmojis,
    ),
    EmojiCategory(
        name = "Education",
        emojis = educationEmojis,
    ),
    EmojiCategory(
        name = "Personal Care",
        emojis = personalCareEmojis,
    ),
    EmojiCategory(
        name = "Pets",
        emojis = petsEmojis,
    ),
    EmojiCategory(
        name = "Miscellaneous",
        emojis = miscellaneousEmojis,
    ),
)

@Composable
fun EmojiPicker(
    onDismiss: () -> Unit,
    onEmojiPicked: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Card(
                modifier = Modifier.wrapContentSize(),
                colors = CardDefaults.cardColors(),
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(10.dp),
                    columns = GridCells.Adaptive(minSize = 45.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    emojis.forEach { emojiCategory ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(text = emojiCategory.name)
                        }
                        items(emojiCategory.emojis) { emoji ->
                            Text(
                                modifier = Modifier
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
        },
    )
}

@DarkModePreview
@Composable
fun PreviewEmojiPicker() {
    FeeBeeTheme {
        EmojiPicker(
            onEmojiPicked = {},
            onDismiss = {},
        )
    }
}
