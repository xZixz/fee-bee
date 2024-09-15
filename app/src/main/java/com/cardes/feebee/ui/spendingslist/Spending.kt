package com.cardes.feebee.ui.spendingslist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cardes.domain.entity.Spending
import com.cardes.feebee.mock.PreviewMockUp
import com.cardes.feebee.ui.theme.FeeBeeTheme
import java.text.SimpleDateFormat
import java.util.Locale

private val spendingDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)

@Composable
fun Spending(
    spending: Spending,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RectangleShape,
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = spending.content,
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "${spending.amount}$",
                )
            }
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = spendingDateFormat.format(spending.time),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                items(spending.categories) { category ->
                    CategoryChip(text = category.name)
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.tertiaryContainer,
            )
            .padding(5.dp),
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
private fun SpendingPreview() {
    FeeBeeTheme {
        Surface {
            Spending(
                spending = PreviewMockUp.spendings[0],
            )
        }
    }
}
