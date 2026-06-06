package com.cardes.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class CategoriesDestination : NavKey {
    @Serializable
    data class EditCategory(val categoryId: Long) : NavKey
}
