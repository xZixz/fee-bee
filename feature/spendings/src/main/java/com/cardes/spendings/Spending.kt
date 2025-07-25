package com.cardes.spendings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardes.data.fake.Fake
import com.cardes.designsystem.component.CategoryChip
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.domain.entity.Spending
import java.math.BigDecimal

@Composable
fun SpendingItem(
    spending: Spending,
    onSpendingClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        onClick = { onSpendingClick(spending.id) },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = spending.content,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Cost(
                amount = spending.amount,
            )
        }
    }
}

@Composable
fun Cost(
    modifier: Modifier = Modifier,
    amount: BigDecimal,
    currency: String = "$",
) {
    val text = buildAnnotatedString {
        append("$amount")
        withStyle(style = SpanStyle(fontSize = 14.sp)) {
            append(" $currency")
        }
    }
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Normal,
        text = text,
    )
}

@Composable
fun CategoryChipsRow(
    spending: Spending,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(spending.categories) { category ->
            CategoryChip(text = category.name)
        }
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
