package com.cardes.spendingdetail.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class SpendingDetailNavKey(val spendingId: Long) : NavKey
