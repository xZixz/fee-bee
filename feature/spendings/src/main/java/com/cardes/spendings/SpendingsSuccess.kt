package com.cardes.spendings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cardes.domain.entity.Spending
import java.util.SortedMap

@Composable
fun SpendingsSuccess(
    spendingsData: SortedMap<DayYear, List<Spending>>,
    onSpendingClick: (Long) -> Unit,
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
) = Box(modifier = Modifier.fillMaxSize()) {
    SpendingsList(
        spendingsData = spendingsData,
        onSpendingClick = onSpendingClick,
    )
    CreateMockDataButtons(
        modifier = Modifier.align(Alignment.BottomEnd),
        onAddSamplesClick = onAddSamplesClick,
        onDeleteAllSpendingsClick = onDeleteAllSpendingsClick,
    )
}
