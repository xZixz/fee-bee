package com.cardes.feebee.ui.spendingslist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardes.domain.entity.Spending
import com.cardes.feebee.mock.PreviewMockUp
import com.cardes.feebee.ui.theme.FeeBeeTheme
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Locale

private val spendingDateFormat = SimpleDateFormat("EEE, dd", Locale.US)

@Composable
fun Spending(
    spending: Spending,
    onSpendingClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(50),
        modifier = modifier.clickable {
            onSpendingClick(spending.id)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = spending.content,
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = spendingDateFormat.format(spending.time),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Cost(
                modifier = Modifier.align(Alignment.CenterVertically),
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
        modifier = modifier.padding(end = 5.dp),
        style = MaterialTheme.typography.titleLarge,
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
            ).padding(5.dp),
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
                onSpendingClick = {},
            )
        }
    }
}
