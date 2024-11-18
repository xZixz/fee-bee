package com.cardes.feebee.ui.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PieChartScreen(
    modifier: Modifier = Modifier,
    viewModel: PieChartViewModel = hiltViewModel()
) {
    val pieChartViewState by viewModel.pieChartViewState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.padding(10.dp),
    ) {
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(5.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            pieChartViewState.categories.forEach { category ->
                FilterChip(
                    selected = pieChartViewState.selectedCategoryId == category.id,
                    onClick = {
                        viewModel.onCategoryClick(category.id)
                    },
                    label = {
                        Text(text = category.name)
                    },
                )
            }
        }
    }
}
