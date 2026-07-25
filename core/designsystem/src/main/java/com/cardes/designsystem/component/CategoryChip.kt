package com.cardes.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.designsystem.theme.Spacing

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier
            .background(
                shape = FeeBeeTheme.shapes.pill,
                color = MaterialTheme.colorScheme.tertiaryContainer,
            )
            .padding(Spacing.xs),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(
    name = "Light Mode",
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun CategoryChipPreview() {
    CategoryChip(text = "Food")
}
