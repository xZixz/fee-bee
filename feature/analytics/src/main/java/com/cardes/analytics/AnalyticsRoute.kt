package com.cardes.analytics

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cardes.analytics.bycategories.ByCategoriesRoute
import com.cardes.analytics.totalspent.TotalSpentScreen
import com.cardes.designsystem.theme.FeeBeeTheme

@Composable
fun AnalyticsRoute(modifier: Modifier = Modifier) {
    AnalyticsScreen(modifier = modifier)
}

@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        var currentTab by rememberSaveable(stateSaver = AnalyticTabDestinationSaver) {
            mutableStateOf(AnalyticTabDestination.BY_CATEGORIES_TAB)
        }

        AnalyticsTopBar(
            tabs = AnalyticTabDestination.entries,
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

enum class AnalyticTabDestination(
    @StringRes val titleStringResourceId: Int,
    val icon: ImageVector,
    val screen: @Composable () -> Unit,
) {
    BAR_CHART_TAB(
        titleStringResourceId = R.string.analytics_bar_chart_title,
        icon = Icons.Filled.Timeline,
        screen = { TotalSpentScreen() },
    ),

    BY_CATEGORIES_TAB(
        titleStringResourceId = R.string.analytics_by_categories,
        icon = Icons.Filled.BarChart,
        screen = { ByCategoriesRoute() },
    ),
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
                tabs = AnalyticTabDestination.entries,
                onSelected = {},
                currentTab = AnalyticTabDestination.BAR_CHART_TAB,
            )
        }
    }
}

private const val INACTIVE_TAB_OPACITY = 0.60f

val AnalyticTabDestinationSaver: Saver<AnalyticTabDestination, Int> = Saver(
    save = { destination -> AnalyticTabDestination.entries.indexOf(destination) },
    restore = { id -> AnalyticTabDestination.entries[id] },
)
