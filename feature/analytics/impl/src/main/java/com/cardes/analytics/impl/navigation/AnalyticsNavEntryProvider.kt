package com.cardes.analytics.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.cardes.analytics.api.AnalyticsNavKey
import com.cardes.analytics.impl.AnalyticsRoute

fun EntryProviderScope<NavKey>.analyticsEntry() {
    entry<AnalyticsNavKey> {
        AnalyticsRoute()
    }
}
