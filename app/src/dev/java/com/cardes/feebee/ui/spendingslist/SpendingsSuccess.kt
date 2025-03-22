package com.cardes.feebee.ui.spendingslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cardes.domain.entity.Spending

@Composable
fun SpendingsSuccess(
    spendings: List<Spending>,
    onSpendingClick: (Long) -> Unit,
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
) = Box(modifier = Modifier.fillMaxSize()) {
    SpendingsList(
        spendings = spendings,
        onSpendingClick = onSpendingClick,
    )
    CreateMockDataButtons(
        modifier = Modifier.align(Alignment.BottomEnd),
        onAddSamplesClick = onAddSamplesClick,
        onDeleteAllSpendingsClick = onDeleteAllSpendingsClick,
    )
}
