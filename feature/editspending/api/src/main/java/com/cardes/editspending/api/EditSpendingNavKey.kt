package com.cardes.editspending.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class EditSpendingNavKey(val spendingId: Long) : NavKey
