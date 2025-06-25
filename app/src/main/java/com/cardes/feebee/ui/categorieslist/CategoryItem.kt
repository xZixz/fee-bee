package com.cardes.feebee.ui.categorieslist

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.domain.entity.Category

private val ItemHeight = 34.dp

@Composable
fun CategoryItem(
    category: Category,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(ItemHeight)
            .clickable { onCategoryClick(category.id) },
        shape = RoundedCornerShape(50),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = category.name,
            )
            if (category.emoji.isBlank()) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.AddReaction,
                    tint = Color.Gray,
                    contentDescription = "Add category icon",
                )
            } else {
                Text(text = category.emoji)
            }
        }
    }
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
private fun CategoryItemPreview(
    @PreviewParameter(CategoryItemPreviewParam::class) category: Category,
) {
    FeeBeeTheme {
        CategoryItem(
            category = category,
            onCategoryClick = {},
        )
    }
}

@Composable
fun AddCategoryItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(ItemHeight)
            .clickable {
                onItemClick()
            },
        shape = RoundedCornerShape(50),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.Gray,
                contentDescription = "Add category button",
            )
        }
    }
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
private fun AddCategoryItemPreview() {
    FeeBeeTheme {
        AddCategoryItem(
            onItemClick = {},
        )
    }
}
