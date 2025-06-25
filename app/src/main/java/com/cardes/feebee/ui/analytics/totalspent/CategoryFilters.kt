package com.cardes.feebee.ui.analytics.totalspent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cardes.feebee.ui.editspending.NoRippleInteractionSource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFilters(
    onCategoryClick: (Long) -> Unit,
    selectCategoryViewStates: List<SelectCategoryViewState>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        selectCategoryViewStates.forEach { categoryViewState ->
            FilterChip(
                selected = categoryViewState.isSelected,
                shape = RoundedCornerShape(50),
                onClick = {
                    onCategoryClick(categoryViewState.category.id)
                },
                label = {
                    Row {
                        Text(categoryViewState.category.name)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(categoryViewState.category.emoji)
                    }
                },
                interactionSource = remember { NoRippleInteractionSource() },
            )
        }
    }
}
