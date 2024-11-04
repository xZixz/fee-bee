package com.cardes.feebee.ui.analytics

import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cardes.feebee.R
import com.cardes.feebee.ui.common.ChartPreview
import com.cardes.feebee.ui.theme.FeeBeeTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shadow
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape

private const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
private const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f

@Composable
private fun TotalSpentBarChart(
    spendingsChartModelProducer: CartesianChartModelProducer?,
    spendingsChartModel: CartesianChartModel?,
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        val currentContext = LocalContext.current
        val valueFormatter = remember {
            CartesianValueFormatter { _, x, _ ->
                currentContext.getString(fromIntToMonthStringResourceId(x.toInt()))
            }
        }
        val chart = rememberCartesianChart(
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
            startAxis = VerticalAxis.rememberStart(
                itemPlacer = remember { VerticalAxis.ItemPlacer.step(shiftTopLines = false) },
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = valueFormatter,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
            ),
            marker = rememberDefaultCartesianMarker(
                label = rememberTextComponent(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlignment = Layout.Alignment.ALIGN_CENTER,
                    padding = dimensions(8.dp, 4.dp),
                    background =
                        rememberShapeComponent(
                            color = MaterialTheme.colorScheme.surfaceBright,
                            shape = markerCorneredShape(Corner.FullyRounded),
                            shadow =
                                shadow(radius = LABEL_BACKGROUND_SHADOW_RADIUS_DP.dp, dy = LABEL_BACKGROUND_SHADOW_DY_DP.dp),
                        ),
                    minWidth = TextComponent.MinWidth.fixed(40.dp),
                ),
            ),
            layerPadding = cartesianLayerPadding(
                scalableStartPadding = 16.dp,
                scalableEndPadding = 16.dp,
            ),
        )
        val scrollState = rememberVicoScrollState()
        val zoomState = rememberVicoZoomState()

        if (spendingsChartModelProducer != null) {
            CartesianChartHost(
                chart = chart,
                scrollState = scrollState,
                zoomState = zoomState,
                modelProducer = spendingsChartModelProducer,
            )
        }

        if (spendingsChartModel != null) {
            CartesianChartHost(
                chart = chart,
                scrollState = scrollState,
                zoomState = zoomState,
                model = spendingsChartModel,
            )
        }
    }
}

@Composable
fun TotalSpentBarChart(
    spendingsChartModelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    TotalSpentBarChart(
        spendingsChartModelProducer = spendingsChartModelProducer,
        spendingsChartModel = null,
        modifier = modifier,
    )
}

@Composable
fun TotalSpentBarChart(
    spendingsChartModel: CartesianChartModel,
    modifier: Modifier = Modifier,
) {
    TotalSpentBarChart(
        spendingsChartModel = spendingsChartModel,
        spendingsChartModelProducer = null,
        modifier = modifier,
    )
}

private fun fromIntToMonthStringResourceId(monthIndex: Int) =
    when (monthIndex) {
        0 -> R.string.january_abbr
        1 -> R.string.february_abbr
        2 -> R.string.march_abbr
        3 -> R.string.april_abbr
        4 -> R.string.may_abbr
        5 -> R.string.june_abbr
        6 -> R.string.july_abbr
        7 -> R.string.august_abbr
        8 -> R.string.september_abbr
        9 -> R.string.october_abbr
        10 -> R.string.november_abbr
        11 -> R.string.december_abbr
        else -> R.string.empty
    }

@ChartPreview
@Composable
fun TotalSpentChartPreview() {
    FeeBeeTheme {
        Surface {
            TotalSpentBarChart(
                spendingsChartModel = CartesianChartModel(
                    ColumnCartesianLayerModel.build {
                        series(
                            x = listOf(4, 5, 6),
                            y = listOf(100, 200, 400),
                        )
                    },
                ),
            )
        }
    }
}
