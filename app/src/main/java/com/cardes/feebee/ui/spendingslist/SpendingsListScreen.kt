package com.cardes.feebee.ui.spendingslist

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cardes.domain.entity.Spending
import com.cardes.feebee.R
import com.cardes.feebee.mock.PreviewMockUp
import com.cardes.feebee.ui.common.BasePage
import com.cardes.feebee.ui.theme.FeeBeeTheme

@Composable
fun SpendingsListRoute(
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    viewModel: SpendingsListViewModel = hiltViewModel(),
) {
    val spendingsListUiState by viewModel.spendingsListUiState.collectAsStateWithLifecycle()
    SpendingsListScreen(
        spendingsListUiState = spendingsListUiState,
        onCreateSpendingClick = onCreateSpendingClick,
        onSpendingClick = onSpendingClick,
        onAddSamplesClick = {
            viewModel.addSamples()
        },
        onDeleteAllSpendingsClick = {
            viewModel.deleteAllSpendings()
        },
    )
}

@Composable
fun SpendingsListScreen(
    spendingsListUiState: SpendingsListUiState,
    onCreateSpendingClick: () -> Unit,
    onSpendingClick: (Long) -> Unit,
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
) {
    when (spendingsListUiState) {
        SpendingsListUiState.Loading -> {
            // TODO: Loading state
        }

        is SpendingsListUiState.Success -> {
            SpendingsList(
                onCreateSpendingClick = onCreateSpendingClick,
                spendings = spendingsListUiState.spendings,
                onSpendingClick = onSpendingClick,
                onAddSamplesClick = onAddSamplesClick,
                onDeleteAllSpendingsClick = onDeleteAllSpendingsClick,
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Preview(
    name = "Dark mode - more width",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 400,
    heightDp = 400,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    widthDp = 250,
    heightDp = 400,
)
@Composable
internal fun SpendingsListScreenPreview() {
    FeeBeeTheme {
        Surface {
            SpendingsListScreen(
                onCreateSpendingClick = {},
                spendingsListUiState = SpendingsListUiState.Success(spendings = PreviewMockUp.spendings),
                onSpendingClick = {},
                onAddSamplesClick = {},
                onDeleteAllSpendingsClick = {},
            )
        }
    }
}

@Composable
fun SpendingsList(
    onCreateSpendingClick: () -> Unit,
    spendings: List<Spending>,
    onSpendingClick: (Long) -> Unit,
    onAddSamplesClick: () -> Unit,
    onDeleteAllSpendingsClick: () -> Unit,
) {
    BasePage(
        title = stringResource(id = R.string.spendings),
        titleAction = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add spending",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 5.dp)
                    .clickable(onClick = onCreateSpendingClick),
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(spendings) { spending ->
                Spending(
                    modifier = Modifier.clickable {
                        onSpendingClick(spending.id)
                    },
                    spending = spending,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            Row {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .weight(1.0f, false)
                        .wrapContentWidth(),
                    onClick = {
                        onAddSamplesClick()
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add 3")
                    },
                    text = {
                        Text(
                            text = "Add samples",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
                Spacer(modifier = Modifier.width(10.dp))
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .weight(1.0f, false)
                        .wrapContentWidth(),
                    onClick = {
                        onDeleteAllSpendingsClick()
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Remove all")
                    },
                    text = {
                        Text(
                            text = "Remove all",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
            }
        }
    }
}
