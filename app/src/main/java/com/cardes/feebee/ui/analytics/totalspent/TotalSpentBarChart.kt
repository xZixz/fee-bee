package com.cardes.feebee.ui.analytics.totalspent

import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cardes.feebee.ui.common.ChartPreview
import com.cardes.feebee.ui.common.StringUtil.fromIntToMonthStringResourceId
import com.cardes.feebee.ui.theme.FeeBeeTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.TextComponent

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
        val lineChart = rememberLineCartesianLayer(
            LineCartesianLayer.LineProvider.series(
                vicoTheme.lineCartesianLayerColors.map { color ->
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(color)) },
                        pointConnector = remember { LineCartesianLayer.PointConnector.cubic(curvature = 0f) },
                    )
                },
            ),
        )
        val chart = rememberCartesianChart(
            lineChart,
            startAxis = VerticalAxis.rememberStart(
                itemPlacer = remember { VerticalAxis.ItemPlacer.step(shiftTopLines = false) },
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = valueFormatter,
                itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
            ),
            marker = marker,
            persistentMarkers = rememberExtraLambda(marker) {
                (0..11).onEach {
                    marker at it
                }
            },
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

@ChartPreview
@Composable
fun TotalSpentChartPreview() {
    FeeBeeTheme {
        Surface {
            TotalSpentBarChart(
                spendingsChartModel = CartesianChartModel(
                    LineCartesianLayerModel.build {
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
