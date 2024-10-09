package com.cardes.feebee.ui.analytics

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.LegendPosition
import com.cardes.feebee.R
import com.cardes.feebee.ui.theme.FeeBeeTheme
import com.cardes.feebee.ui.theme.Typography
import java.math.BigDecimal

@Composable
fun AnalyticsRoute(modifier: Modifier = Modifier) {
    AnalyticsScreen(modifier = modifier)
}

@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        var currentTab by remember { mutableStateOf<AnalyticTabDestination>(AnalyticTabDestination.BarChartTabDestination) }
        AnalyticsTopBar(
            tabs = allAnalyticTabDestinations,
            currentTab = currentTab,
            onSelected = { selectedTab ->
                if (currentTab != selectedTab) currentTab = selectedTab
            },
        )
        currentTab.screen()
    }
}

@Composable
fun AnalyticsTopBar(
    tabs: List<AnalyticTabDestination>,
    onSelected: (AnalyticTabDestination) -> Unit,
    currentTab: AnalyticTabDestination,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { destination ->
            AnalyticTab(
                modifier = Modifier.weight(1.0f),
                title = stringResource(destination.titleStringResourceId),
                icon = destination.icon,
                onSelected = {
                    onSelected(destination)
                },
                selected = currentTab == destination,
            )
        }
    }
}

@Composable
fun AnalyticTab(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    val color = MaterialTheme.colorScheme.onSurface
    val tintColor = if (selected) color else color.copy(alpha = INACTIVE_TAB_OPACITY)
    val backgroundColor = MaterialTheme.colorScheme.surfaceContainer
    val backgroundTintColor =
        if (selected) backgroundColor else backgroundColor.copy(alpha = INACTIVE_TAB_OPACITY)
    Row(
        modifier = modifier
            .background(color = backgroundTintColor)
            .fillMaxHeight()
            .clickable(
                indication = null,
                onClick = {
                    onSelected()
                },
                interactionSource = remember { MutableInteractionSource() },
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = "$title tab", tint = tintColor)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = title, color = tintColor)
    }
}

sealed class AnalyticTabDestination(
    @StringRes val titleStringResourceId: Int,
    val icon: ImageVector,
    val screen: @Composable () -> Unit,
) {
    data object BarChartTabDestination : AnalyticTabDestination(
        titleStringResourceId = R.string.analytics_bar_chart_title,
        icon = Icons.Filled.BarChart,
        screen = { TotalSpentScreen() },
    )

    data object PieChartTabDestination : AnalyticTabDestination(
        titleStringResourceId = R.string.analytics_pie_chart_title,
        icon = Icons.Filled.PieChart,
        screen = { PieChartScreen() },
    )
}

private val allAnalyticTabDestinations = listOf(
    AnalyticTabDestination.BarChartTabDestination,
    AnalyticTabDestination.PieChartTabDestination,
)

@Composable
fun TotalSpentScreen(
    modifier: Modifier = Modifier,
    viewModel: TotalSpentViewModel = hiltViewModel(),
) {
    val spendingsData by viewModel.spendingsByMonthData.collectAsStateWithLifecycle()
    when {
        spendingsData.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.no_data),
                )
            }
        }

        else -> {
            TotalSpentBarChart(
                modifier = modifier.padding(10.dp),
                spendingsData = spendingsData,
            )
        }
    }
}

@Composable
fun PieChartScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = Icons.Filled.PieChart,
            contentDescription = "",
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 400,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 400,
)
@Composable
private fun AnalyticTabTopBarPreview() {
    FeeBeeTheme {
        Surface {
            AnalyticsTopBar(
                tabs = allAnalyticTabDestinations,
                onSelected = {},
                currentTab = AnalyticTabDestination.BarChartTabDestination,
            )
        }
    }
}

@Composable
fun TotalSpentBarChart(
    modifier: Modifier = Modifier,
    spendingsData: Map<Int, BigDecimal>,
) {
    val barParameter = BarParameters(
        dataName = "",
        data = spendingsData.values.map { it.toDouble() },
        barColor = MaterialTheme.colorScheme.onSurface,
    )
    Box(modifier = modifier) {
        BarChart(
            chartParameters = listOf(barParameter),
            gridColor = Color.DarkGray,
            xAxisData = spendingsData.keys.map { stringResource(fromIntToMonthStringResourceId(it)) },
            isShowGrid = true,
            animateChart = false,
            showGridWithSpacer = true,
            yAxisStyle = Typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            xAxisStyle = Typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            legendPosition = LegendPosition.DISAPPEAR,
            spaceBetweenGroups = 20.dp,
            yAxisRange = 5,
            barWidth = 20.dp,
            barCornerRadius = 3.dp,
        )
    }
}

private const val INACTIVE_TAB_OPACITY = 0.60f

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
