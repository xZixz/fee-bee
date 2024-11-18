package com.cardes.feebee.ui.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.monthDayYearDisplayFormat
import com.cardes.feebee.ui.design.component.FeeBeeDateInput
import com.cardes.feebee.ui.editspending.NoRippleInteractionSource

@Composable
fun TotalSpentScreen(
    modifier: Modifier = Modifier,
    viewModel: TotalSpentViewModel = hiltViewModel(),
) {
    val spendingsData by viewModel.spendingsData.collectAsStateWithLifecycle()
    val spendingsChartModelProducer = viewModel.spendingsChartModelProducer
    val totalSpentViewState by viewModel.totalSpentViewState.collectAsStateWithLifecycle()
    Column(modifier = modifier.padding(horizontal = 10.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        when {
            spendingsData.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.no_data),
                    )
                }
            }

            else -> TotalSpentBarChart(spendingsChartModelProducer = spendingsChartModelProducer)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(5.dp))
        CategoryFilters(
            onCategoryClick = viewModel::onCategoryClick,
            selectCategoryViewStates = totalSpentViewState.selectCategoryViewStates,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.from),
            style = MaterialTheme.typography.titleMedium,
        )
        FeeBeeDateInput(
            time = totalSpentViewState.fromDate,
            dateFormat = remember { monthDayYearDisplayFormat },
            onDatePicked = viewModel::onFromDatePicked,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.to),
            style = MaterialTheme.typography.titleMedium,
        )
        FeeBeeDateInput(
            time = totalSpentViewState.toDate,
            dateFormat = remember { monthDayYearDisplayFormat },
            onDatePicked = viewModel::onToDatePicked,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryFilters(
    onCategoryClick: (Long) -> Unit,
    selectCategoryViewStates: List<SelectCategoryViewState>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        selectCategoryViewStates.forEach { categoryViewState ->
            FilterChip(
                selected = categoryViewState.isSelected,
                onClick = {
                    onCategoryClick(categoryViewState.category.id)
                },
                label = {
                    Text(categoryViewState.category.name)
                },
                interactionSource = remember { NoRippleInteractionSource() },
            )
        }
    }
}
