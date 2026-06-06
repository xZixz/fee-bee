package com.cardes.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class SpendingsDestination : NavKey {
    @Serializable
    data class SpendingDetails(
        val spendingId: Long,
    ) : SpendingsDestination()

    @Serializable
    data class EditSpending(
        val spendingId: Long,
    ) : SpendingsDestination()
}
