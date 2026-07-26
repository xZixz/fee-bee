package com.cardes.spendings.impl

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cardes.data.fake.Fake
import com.cardes.designsystem.component.CategoryChip
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.designsystem.theme.Spacing
import com.cardes.domain.entity.Spending
import java.math.BigDecimal

@Composable
fun SpendingItem(
    spending: Spending,
    onSpendingClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
            .clickable {
                onSpendingClick(spending.id)
            }
            .padding(
                horizontal = Spacing.s,
                vertical = Spacing.s,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = spending.content,
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Cost(amount = spending.amount)
    }
}

@Composable
private fun Cost(
    modifier: Modifier = Modifier,
    amount: BigDecimal,
    currency: String = "$",
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        text = "$amount $currency",
    )
}

@Composable
fun CategoryChipsRow(
    spending: Spending,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        items(spending.categories) { category ->
            CategoryChip(text = category.name)
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
private fun SingleSpendingPreview() {
    FeeBeeTheme {
        Surface {
            SpendingItem(
                spending = Fake.spendings[0],
                onSpendingClick = {},
            )
        }
    }
}
