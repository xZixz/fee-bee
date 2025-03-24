package com.cardes.feebee.ui.spendingslist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.domain.entity.Spending
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.theme.FeeBeeTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.SortedMap
import kotlin.math.absoluteValue

@Composable
fun SpendingsRoute(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    viewModel: SpendingsListViewModel = hiltViewModel(),
) {
    val spendingsListUiState by viewModel.spendingsListUiState.collectAsStateWithLifecycle()
    SpendingsScreen(
        spendingsListUiState = spendingsListUiState,
        onCreateSpendingClick = onCreateSpendingClick,
        onSpendingClick = onSpendingClick,
        onAddSamplesClick = {
            viewModel.addSamples()
        },
        onDeleteAllSpendingsClick = {
            viewModel.deleteAllSpendings()
        },
    )
}

@Composable
fun SpendingsScreen(
    spendingsListUiState: SpendingsListUiState,
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
) {
    BasePage(
        title = stringResource(id = R.string.spendings),
        titleAction = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add spending",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 5.dp)
                    .clickable(onClick = onCreateSpendingClick),
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (spendingsListUiState) {
                SpendingsListUiState.Loading -> {
                    // TODO: Loading state
                }

                is SpendingsListUiState.Success -> {
                    SpendingsSuccess(
                        spendingsData = spendingsListUiState.data,
                        onSpendingClick = onSpendingClick,
                        onAddSamplesClick = onAddSamplesClick,
                        onDeleteAllSpendingsClick = onDeleteAllSpendingsClick,
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
    name = "Dark mode - more width",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 400,
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
internal fun SpendingsListScreenPreview(
    @PreviewParameter(SpendingsPreviewParam::class) spendingsData: SortedMap<DayYear, List<Spending>>,
) {
    FeeBeeTheme {
        Surface {
            SpendingsScreen(
                onCreateSpendingClick = {},
                spendingsListUiState = SpendingsListUiState.Success(data = spendingsData),
                onSpendingClick = {},
                onAddSamplesClick = {},
                onDeleteAllSpendingsClick = {},
            )
        }
    }
}

private val stickyHeaderMonthFormat = SimpleDateFormat("MMMM d", Locale.US)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpendingsList(
    modifier: Modifier = Modifier,
    spendingsData: SortedMap<DayYear, List<Spending>>,
    onSpendingClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        spendingsData.forEach { (dayYear, spendingsByDate) ->
            stickyHeader {
                with(Calendar.getInstance()) {
                    set(Calendar.DAY_OF_YEAR, dayYear.dayOfYear)
                    set(Calendar.YEAR, dayYear.year)
                    val stickyText = buildAnnotatedString {
                        append(stickyHeaderMonthFormat.format(time))
                        withStyle(
                            SpanStyle(
                                fontSize = 9.sp,
                                baselineShift = BaselineShift.Superscript,
                            ),
                        ) {
                            append(get(Calendar.DAY_OF_MONTH).ordinal())
                        }
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(top = 20.dp, bottom = 2.dp),
                        text = stickyText,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            items(
                items = spendingsByDate,
                key = { spending -> spending.id },
            ) { spending ->
                SpendingItem(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    spending = spending,
                    onSpendingClick = onSpendingClick,
                )
            }
        }
    }
}

private fun Int.ordinal(): String =
    when (absoluteValue % 100) {
        in 11..13 -> "th"
        else -> when (absoluteValue % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
