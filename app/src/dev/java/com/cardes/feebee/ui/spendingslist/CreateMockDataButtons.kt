package com.cardes.feebee.ui.spendingslist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateMockDataButtons(
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
    ) {
        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier
                .weight(1.0f, false)
                .wrapContentWidth(),
            onClick = {
                onAddSamplesClick()
            },
            content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add 3")
            },
        )
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(
            shape = CircleShape,
            modifier = Modifier
                .weight(1.0f, false)
                .wrapContentWidth(),
            onClick = {
                onDeleteAllSpendingsClick()
            },
            content = {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Remove all")
            },
        )
    }
}
