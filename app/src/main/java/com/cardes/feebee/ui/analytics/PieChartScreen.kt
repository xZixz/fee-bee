package com.cardes.feebee.ui.analytics

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.monthYearDisplayFormat
import com.cardes.feebee.ui.design.component.FeeBeeTextInput
import com.cardes.feebee.ui.theme.FeeBeeTheme
import com.chargemap.compose.numberpicker.NumberPicker
import java.util.Calendar

@Composable
fun PieChartScreen(
    modifier: Modifier = Modifier,
    viewModel: PieChartViewModel = hiltViewModel(),
) {
    val pieChartViewState by viewModel.pieChartViewState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.padding(10.dp),
    ) {
        MonthSelection(
            month = pieChartViewState.selectedMonth.month,
            year = pieChartViewState.selectedMonth.year,
            onMonthPicked = viewModel::onMonthPicked,
        )
        Spacer(modifier = Modifier.height(5.dp))
        CategorySelection(
            selectCategoryViewState = pieChartViewState.selectCategoryViewState,
            onCategoryClick = viewModel::onCategoryClick,
        )

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySelection(
    selectCategoryViewState: PieChartViewState.SelectCategoryViewState,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(5.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            selectCategoryViewState.categories.forEach { category ->
                FilterChip(
                    selected = selectCategoryViewState.selectedCategoryId == category.id,
                    onClick = {
                        onCategoryClick(category.id)
                    },
                    label = {
                        Text(text = category.name)
                    },
                )
            }
        }
    }
}

@Composable
fun MonthSelection(
    month: Int,
    year: Int,
    onMonthPicked: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showMonthYearPickerDialog by remember { mutableStateOf(false) }
    val monthText = remember(month, year) {
        Calendar.getInstance().apply {
            set(Calendar.MONTH, month - 1)
            set(Calendar.YEAR, year)
        }.run { monthYearDisplayFormat.format(time) }
    }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.select_the_month),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.wrapContentWidth()) {
            val focusRequester = remember { FocusRequester() }
            val interactionSource = remember { MutableInteractionSource() }
            FeeBeeTextInput(
                title = stringResource(id = R.string.date),
                text = monthText,
                onTextChanged = {},
                readOnly = true,
                modifier = Modifier
                    .defaultMinSize(minWidth = 50.dp)
                    .focusRequester(focusRequester),
            )
            Box(
                Modifier
                    .matchParentSize()
                    .clickable(
                        onClick = {
                            showMonthYearPickerDialog = true
                            focusRequester.requestFocus()
                        },
                        interactionSource = interactionSource,
                        indication = null,
                    ),
            ) { }
        }
    }

    if (showMonthYearPickerDialog) {
        MonthYearPickerDialog(
            initialMonth = month,
            initialYear = year,
            onDismiss = { showMonthYearPickerDialog = false },
            onConfirm = { month, year ->
                showMonthYearPickerDialog = false
                onMonthPicked(month, year)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MonthYearPickerDialog(
    initialMonth: Int,
    initialYear: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
) {
    var selectedMonth by remember { mutableIntStateOf(initialMonth) }
    var selectedYear by remember { mutableIntStateOf(initialYear) }
    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.month))
                    NumberPicker(
                        value = selectedMonth,
                        dividersColor = MaterialTheme.colorScheme.outline,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = LocalContentColor.current),
                        onValueChange = { selectedMonth = it },
                        range = 1..12,
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.year))
                    NumberPicker(
                        dividersColor = MaterialTheme.colorScheme.outline,
                        value = selectedYear,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = LocalContentColor.current),
                        onValueChange = { selectedYear = it },
                        range = 2000..currentYear,
                    )
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    onConfirm(selectedMonth, selectedYear)
                },
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun MonthYearPickerDialogPreview() {
    FeeBeeTheme {
        Surface {
            MonthYearPickerDialog(
                initialMonth = 10,
                initialYear = 2024,
                onDismiss = {},
                onConfirm = { _, _ -> },
            )
        }
    }

}
