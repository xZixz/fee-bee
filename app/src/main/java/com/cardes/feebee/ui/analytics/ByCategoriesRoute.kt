package com.cardes.feebee.ui.analytics

import android.content.res.Configuration
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import java.util.Calendar

@Composable
fun ByCategoriesRoute(
    modifier: Modifier = Modifier,
    viewModel: ByCategoriesViewModel = hiltViewModel(),
) {
    val selectedMonth by viewModel.selectedMonth.collectAsStateWithLifecycle()
    val chartViewState by viewModel.chartViewState.collectAsStateWithLifecycle()

    ByCategoryScreen(
        selectedMonth = selectedMonth,
        chartViewState = chartViewState,
        totalSpentByCategoryChartProducer = viewModel.totalSpentByCategoryInMonthModelProducer,
        onMonthPicked = viewModel::onMonthPicked,
        modifier = modifier,
    )
}

@Composable
fun ByCategoryScreen(
    selectedMonth: MonthYear,
    chartViewState: ByCategoryChartViewState,
    totalSpentByCategoryChartProducer: CartesianChartModelProducer,
    onMonthPicked: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(10.dp),
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            when (chartViewState) {
                is ByCategoryChartViewState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is ByCategoryChartViewState.Error -> {
                    Text(text = stringResource(R.string.something_went_wrong))
                    Text(text = stringResource(R.string.inside_bracket, chartViewState.message))
                }

                is ByCategoryChartViewState.NoData -> {
                    Text(text = stringResource(R.string.no_spending_this_month))
                }

                is ByCategoryChartViewState.Data -> {
                    TotalSpentByCategoriesChart(
                        totalSpentByCategoriesProducer = totalSpentByCategoryChartProducer,
                        categoryNames = chartViewState.categoryNames,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        MonthSelection(
            month = selectedMonth.month,
            year = selectedMonth.year,
            onMonthPicked = onMonthPicked,
        )
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
        Calendar
            .getInstance()
            .apply {
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, 1)
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

@Composable
fun TotalSpentByCategoriesChart(
    modifier: Modifier = Modifier,
    totalSpentByCategoriesProducer: CartesianChartModelProducer,
    categoryNames: List<String>,
) {
    Box(modifier = modifier) {
        val valueFormatter = remember(categoryNames) {
            CartesianValueFormatter { _, x, _ ->
                categoryNames[x.toInt()]
            }
        }
        val marker = rememberDefaultCartesianMarker(
            labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
            label = rememberTextComponent(
                color = MaterialTheme.colorScheme.onSurface,
                textAlignment = Layout.Alignment.ALIGN_CENTER,
                margins = dimensions(bottom = 5.dp),
                padding = dimensions(4.dp, 4.dp),
                minWidth = TextComponent.MinWidth.fixed(40.dp),
            ),
        )
        val barChart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    vicoTheme.columnCartesianLayerColors.map { color ->
                        rememberLineComponent(
                            color = color,
                            thickness = 8.dp,
                            shape = CorneredShape.rounded(
                                topLeft = 3.dp,
                                topRight = 3.dp,
                            ),
                        )
                    },
                ),
            ),
            persistentMarkers = rememberExtraLambda {
                (0..categoryNames.size - 1).forEach { marker at it }
            },
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = valueFormatter,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
            ),
            marker = marker,
            layerPadding = cartesianLayerPadding(
                scalableStartPadding = 16.dp,
                scalableEndPadding = 16.dp,
            ),
        )
        CartesianChartHost(
            modifier = modifier,
            modelProducer = totalSpentByCategoriesProducer,
            chart = barChart,
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
                ).padding(16.dp),
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
