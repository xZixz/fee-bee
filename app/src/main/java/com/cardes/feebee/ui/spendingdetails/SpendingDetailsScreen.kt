package com.cardes.feebee.ui.spendingdetails

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.designsystem.component.CategoryChip
import com.cardes.designsystem.theme.FeeBeeTheme
import com.cardes.feebee.ui.common.BasePage

@Composable
fun SpendingDetailsRoute(
    onEditClick: () -> Unit,
    spendingDetailsViewModel: SpendingDetailsViewModel = hiltViewModel(),
) {
    val spendingUiState by spendingDetailsViewModel.spendingUiState.collectAsStateWithLifecycle()

    SpendingDetailsScreen(
        spendingUiState = spendingUiState,
        onEditClick = onEditClick,
    )
}

@Composable
fun SpendingDetailsScreen(
    spendingUiState: SpendingUiState,
    onEditClick: () -> Unit,
) {
    when (spendingUiState) {
        is SpendingUiState.Loading -> {}
        is SpendingUiState.Success -> {
            SpendingDetails(
                spendingUiState = spendingUiState,
                onEditClick = onEditClick,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpendingDetails(
    spendingUiState: SpendingUiState.Success,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
) {
    BasePage(
        modifier = modifier,
        title = spendingUiState.description,
        titleAction = {
            Icon(
                modifier = Modifier.clickable {
                    onEditClick()
                },
                imageVector = Icons.Default.Edit,
                contentDescription = "Enter Edit mode",
            )
        },
    ) {
        Text(
            text = "${spendingUiState.cost} $",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = spendingUiState.date,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            spendingUiState.categories.forEach { category ->
                CategoryChip(text = category.name)
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    heightDp = 400,
    widthDp = 250,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 400,
    widthDp = 250,
)
@Composable
private fun SpendingDetailsPreview(
    @PreviewParameter(SpendingDetailsUiStatePreviewParameter::class)
    spendingUiState: SpendingUiState.Success,
) {
    FeeBeeTheme {
        Surface {
            SpendingDetails(
                spendingUiState = spendingUiState,
                onEditClick = {},
            )
        }
    }
}
